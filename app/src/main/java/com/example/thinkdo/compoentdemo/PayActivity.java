//package com.example.thinkdo.compoentdemo;
//
//import android.app.Activity;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//
//import com.braintreepayments.api.BraintreeFragment;
//import com.braintreepayments.api.exceptions.BraintreeError;
//import com.braintreepayments.api.exceptions.ErrorWithResponse;
//import com.braintreepayments.api.exceptions.InvalidArgumentException;
//import com.braintreepayments.api.interfaces.BraintreeCancelListener;
//import com.braintreepayments.api.interfaces.BraintreeErrorListener;
//import com.braintreepayments.api.interfaces.ConfigurationListener;
//import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
//import com.braintreepayments.api.models.Configuration;
//import com.braintreepayments.api.models.PaymentMethodNonce;
//
///**
// * Created by xh on 2018/4/1.
// */
//
//public class PayActivity extends Activity implements PaymentMethodNonceCreatedListener, ConfigurationListener, BraintreeCancelListener, BraintreeErrorListener {
//    BraintreeFragment mBraintreeFragment;
//    String mAuthorization = "";
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        try {
//            mBraintreeFragment = BraintreeFragment.newInstance(this, mAuthorization);
//            mBraintreeFragment.addListener(this);
//            // mBraintreeFragment is ready to use!
//        } catch (InvalidArgumentException e) {
//            // There was an issue with your authorization string.
//        }
//    }
//
//
//    @Override
//    public void onCancel(int requestCode) {
//
//    }
//
//    @Override
//    public void onError(Exception error) {
//        if (error instanceof ErrorWithResponse) {
//            ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;
//            BraintreeError cardErrors = errorWithResponse.errorFor("creditCard");
//            if (cardErrors != null) {
//                // There is an issue with the credit card.
//                BraintreeError expirationMonthError = cardErrors.errorFor("expirationMonth");
//                if (expirationMonthError != null) {
//                    // There is an issue with the expiration month.
////                    setErrorMessage(expirationMonthError.getMessage());
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public void onConfigurationFetched(Configuration configuration) {
//
//    }
//
//    @Override
//    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
//        String nonce = paymentMethodNonce.getNonce();
//
//    }
//}
