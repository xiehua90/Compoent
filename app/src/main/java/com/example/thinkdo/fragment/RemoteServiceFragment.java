package com.example.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.android.architecture.blueprints.todoapp.service.IRemoteService;
import com.example.thinkdo.compoentdemo.R;

public class RemoteServiceFragment extends Fragment {
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            remoteService = IRemoteService.Stub.asInterface(iBinder);
            isBinder = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBinder = false;
        }
    };

    private ServiceConnection messengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messenger = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messenger = null;
        }
    };

    private IRemoteService remoteService;
    private boolean isBinder = false;
    private Messenger messenger;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachMessengerService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_remote_service, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView tv = view.findViewById(R.id.textView);
        Button btn = view.findViewById(R.id.button);

        Switch swi = view.findViewById(R.id.swi);
        swi.setOnCheckedChangeListener((v, check)->{
            if (check){
                attachService();
            }
            else{
                getActivity().unbindService(connection);
                tv.setText("");
            }
        });

        btn.setOnClickListener(l -> {
            if (isBinder) {
                try {
                    tv.setText(remoteService.sayHello() + "" + remoteService.add(1, 2));
                } catch (RemoteException e) {
                    tv.setText("RemoteException");
                }
            } else {
                tv.setText("Service is not bind yet");
            }
        });

        Button btn2 = view.findViewById(R.id.button2);

        btn2.setOnClickListener(l->{
            Message message = Message.obtain();
            message.what =0x10;
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }

    private void attachService() {
        Intent it = new Intent();
        it.setAction("com.example.android.architecture.blueprints.todoapp.service");
        it.setPackage("com.example.android.architecture.blueprints.tododatabinding");
        getActivity().bindService(it, connection, Activity.BIND_AUTO_CREATE);
    }

    private void attachMessengerService() {
        Intent it = new Intent();
        it.setAction("com.example.android.architecture.blueprints.todoapp.messenger");
        it.setPackage("com.example.android.architecture.blueprints.tododatabinding");
        getActivity().bindService(it, messengerConnection, Activity.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(messengerConnection);
    }
}
