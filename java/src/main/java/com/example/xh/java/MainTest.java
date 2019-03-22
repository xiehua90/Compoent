package com.example.xh.java;

import java.text.SimpleDateFormat;
import java.util.Date;


import javax.inject.Inject;




public class MainTest {


    public static void main(String[] args) {

        float a = 0.6f;
        System.out.println(String.format("%.0f", a));

//        for (int i = 0; i < 100; i++) {
//            Calendar calendar = Calendar.getInstance();
//            System.out.println(calendar.getTimeInMillis());
//            calendar.set(Calendar.MILLISECOND, 0);
//            System.out.println(calendar.getTimeInMillis());
//        }


//        rxJavaTest();
//        daggerTest();

//        get(0xff00);


//    static void get(int i) {
////        println(String.format("%x, %x", i, i));
//
//    }
//
//
//    static void abc() {
//        for (int i = 0; i < 256; i++) {
//            int tem = set(i);
//            int ori = reset(tem);
//            System.out.println(String.format("i = %d, set= %d, reset=%d", i, tem, ori));
//
//        }
//    }

//    static int set(int value) {
//        return value | 0x02;
//    }
//
//    static int reset(int value) {
//        return value & 0xfffffffd;
//    }

//        println(new Container2().toString());

//
//        TbComponent component = Dagg.Builder().build();
//        component.make().toString();

    }
//
//    public static void daggerTest() {
//        new CoffeeShop().makeCoffee();
//
//        DaggerZooComponent.builder();
//    }


    static class Container2 {
        @Inject
        DragClass container;

        @Override
        public String toString() {
            return container.toString();
        }
    }


    public static void rxJavaTest() {

//        Flowable.just("Hello world").subscribe(System.out::println);
//
//        Flowable.just("Hello World")
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        System.out.println(s);
//                    }
//                });

//        //测试observeOn subscribeOn方法,
//        // subscribeOn方法只会生效一次且只有第一次的设置有效
//        //observeOn方法可以多次调用生效
//        println(Thread.currentThread().getName());
//        Observable.just("hello")
//                .map(it -> {
//                    println(it + " " + Thread.currentThread().getName());
//                    return it + " world";
//                })
//                .observeOn(Schedulers.computation())
////                .subscribeOn(Schedulers.computation())
//                .map(it -> {
//                    println(it + " " + Thread.currentThread().getName());
//                    return it + " !";
//                })
//                .subscribeOn(Schedulers.io())
////                .observeOn(Schedulers.io())
//                .map(it -> {
//                    println(it + " " + Thread.currentThread().getName());
//                    return it + " one";
//                })
//                .observeOn(Schedulers.newThread())
////                .subscribeOn(Schedulers.newThread())
//                .map(it -> {
//                    println(it + " " + Thread.currentThread().getName());
//                    return it + " two";
//                })
//                .subscribe(it -> println(it + " " + Thread.currentThread().getName()));
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //Parallel processing

//        //rxjava操作符测试
//        //map
//        Observable.just(10)
//                .map(v->"Hello world")
//                .blockingSubscribe(System.out::print);

//        Observable<Integer> ob1 = Observable.create(emitter -> {
//            emitter.onNext(2);
//            emitter.onNext(3);
//            emitter.onNext(4);
//            emitter.onComplete(); //concat必须等第一个Observable结束后才会开始第二个
//        });
//        Observable<Integer> ob2 = Observable.create(emitter -> {
//            emitter.onNext(8);
//            emitter.onNext(9);
//            emitter.onNext(10);
//        });
//        Observable ob1 = Observable.just(1);
//        Observable ob2 = Observable.just(8);
//        ob1.concatWith(ob2).subscribe(System.out::println);
//        Observable.concat(ob1, ob2).subscribe(System.out::println);

//        //concat
//        Observable<String> ob1 = Observable.just("zhang", "Li", "wu");
//        Observable<Integer> ob2 = Observable.just(6, 7, 8, 10);
//        ob1.zipWith(ob2,
//                (arg1, arg2) -> {
//                    println("arg1 = " + arg1 + ", arg2 = " + arg2);
//                    return arg1 + arg2;
//                }
//        ).blockingSubscribe(System.out::println);

//        //interval
//        Observable<Integer> ob1 = Observable.just(1, 2, 3);
//        Observable<Integer> ob2 = Observable.just(6, 7, 8, 10);
//
//        println(getDate("HH:mm:ss")+"   This is start Time");
//        Observable.interval(5, 2, TimeUnit.SECONDS)
//                .map(it -> getDate("HH:mm:ss"))
//                .blockingSubscribe(System.out::println);

//        //distinct
//        Observable.just(1, 6, 1, 7, 8, 6, 10)
//                .distinct(it -> it % 5)
//                .subscribe(System.out::println);


//        //distinct
//        Observable.just(1, 6, 1, 7, 8, 6, 10)
//                .filter(it -> it % 2 == 0)
//                .subscribe(System.out::println);

//        //buffer
//        Observable.range(1, 10)
//                .buffer(4, 3)
//                .filter(it -> it.size() > 3)
//                .flatMap(it -> Observable.just(it))
//                .subscribe(System.out::println);

//        //doOnNext
//        Observable.just(1, 2, 3)
//                .doOnNext(it -> System.out.println("doOnNext:" + it))
//                .subscribe(System.out::println);

//                //skip
//        Observable.range(1, 10)
//                .skip(7)
//                .subscribe(System.out::println);

//        //take
//        Observable.range(1, 10)
//                .take(5)
//                .subscribe(System.out::println);

//        //debounce 如下一次发射间隔小于 timeout,则忽略此次事件
//        Observable.create(emitter -> {
//            emitter.onNext(1); // skip
//            Thread.sleep(400);
//            emitter.onNext(2); // deliver
//            Thread.sleep(505);
//            emitter.onNext(3); // skip
//            Thread.sleep(100);
//            emitter.onNext(4); // deliver
//            Thread.sleep(605);
//            emitter.onNext(5); // deliver
//            Thread.sleep(510);
//            emitter.onComplete();
//        }).debounce(300, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .blockingSubscribe(System.out::println);

//        //defer
//        Observable.defer(() -> Observable.just(1, 2, 3))
//                .subscribe(System.out::println);

//        //merge
//        Observable<Integer> ob1 = Observable.create((ObservableEmitter<Integer> emitter) -> {
//            System.out.println("ob1" + Thread.currentThread().getName());
//            emitter.onNext(1);
//            Thread.sleep(200);
//            emitter.onNext(2);
//            emitter.onNext(3);
//            emitter.onNext(4);
//            emitter.onComplete();
//        }).subscribeOn(Schedulers.newThread());
//
//        Observable<Integer> ob2 = Observable.create((ObservableEmitter<Integer> emitter) -> {
//            System.out.println("ob2" + Thread.currentThread().getName());
//
//            emitter.onNext(6);
//            emitter.onNext(7);
//            emitter.onNext(8);
////            Thread.sleep(200);
//            emitter.onNext(9);
//            emitter.onComplete();
//        }).subscribeOn(Schedulers.newThread());
//
//        ob1.mergeWith(ob2)
//                .blockingSubscribe(it -> System.out.println(it + "  " + Thread.currentThread().getName()));

        //distinct
//        //reduce
//        Observable.range(2, 4)
//                .reduce(5, (a, b) -> {
//                    System.out.println("a = " + a + ", b = " + b);
//                    return a * b;
//                })
//                .subscribe(System.out::println);

//        //scan
//        Observable.range(2, 4)
//                .scan(5, (a, b) -> {
//                    System.out.println("a = " + a + ", b = " + b);
//                    return a * b;
//                })
//                .subscribe(System.out::println);

//        //window
//        Observable.create(emitter -> {
//            int i = 1;
//            println("emitter-->" + Thread.currentThread().getName());
//            emitter.onNext(i++);
////            Thread.sleep(100);
//            emitter.onNext(i++);
//            emitter.onNext(i++);
////            Thread.sleep(300);
//            emitter.onNext(i++);
////            Thread.sleep(200);
//            emitter.onNext(i++);
//            emitter.onNext(i++);
//            emitter.onNext(i++);
////            Thread.sleep(100);
//            emitter.onNext(i++);
//            emitter.onNext(i++);
//            emitter.onNext(i);
////            Thread.sleep(200);
//
//            emitter.onComplete();
//        })
//                .subscribeOn(Schedulers.newThread())
//                .window(5, 3)
//                .subscribe(it -> {
//                    println(it + " " + Thread.currentThread().getName());
//                    it
////                            .subscribeOn(Schedulers.newThread())
//                            .subscribe(i -> System.out.println(i + " " + Thread.currentThread().getName()));
//                });


//        Observable.create(emitter -> {
//            for (int i = 0; i < 10; i++) {
//                emitter.onNext(i);
//                if (i == 5) Thread.sleep(100);
//            }
//            emitter.onComplete();
//        })
//                .subscribeOn(Schedulers.newThread())
//                .mergeWith(Observable.range(20, 3).subscribeOn(Schedulers.newThread()))
//                .subscribe(System.out::println);


//        //flatMap 与 concatMap
//        Observable.range(1,10)
//                .flatMap(it -> Observable.just(it * it))
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(System.out::println);
//        Observable.range(1,10)
//                .concatMap(it -> Observable.just(it * it))
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(System.out::println);

//// defer
////Unfortunately, this prints 0 because Single.just(count.get()) is evaluated at assembly time when the dataflow hasn't even run yet.
/// We need something that defers the evaluation of this Single source until runtime when the main source completes:
//        AtomicInteger count = new AtomicInteger();
//        Observable.range(1, 10)
//                .doOnNext(ignored -> count.incrementAndGet())
//                .ignoreElements()
//                .andThen(Single.defer(() -> Single.just(count.get())))
//                .subscribe(System.out::println);

//        Observable.range(1, 10)
//                .doOnNext(ignored -> count.incrementAndGet())
//                .ignoreElements()
//                .andThen(Single.defer(() -> Single.just(count.get())))
//                .subscribe(System.out::println);

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        //switchMap 当源Observable发射一个新的数据项时 如果旧数据项订阅还未完成，
//        // 就取消旧订阅数据和停止监视那个数据项产生的Observable,开始监视新的数据项.
//        Observable.range(1, 10)
//                .switchMap(it -> Observable.just(it).subscribeOn(Schedulers.newThread()))
//                .blockingSubscribe(System.out::println);

//        Observable.range(3, 10)
//                .buffer(7, 7)
//                .flatMapIterable(it -> {
//                    println(it+" "+Thread.currentThread().getName());
//                    return it;
//                })
////                .concatMapIterable(it -> {
////                    println(it+" "+Thread.currentThread().getName());
////                    return it;
////                })
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(System.out::println);

//        Observable.just(new Man("just()", 0));
//        Observable.create(emitter -> emitter.onNext(new Man("create()", 0)))
////                .subscribe(System.out::println)
//        ;


//        ConnectableObservable<Long> observable = Observable.create((ObservableEmitter<Long> e) ->
//                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
//                        .take(Integer.MAX_VALUE)
//                        .subscribe(e::onNext)
//        ).observeOn(Schedulers.newThread()).publish();
//        observable.connect();
//        Disposable disposable = observable.subscribe(it -> println("one:" + it));
//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        observable.subscribe(it -> println("         two:" + it));
//
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }


    public void publisherAndsubscriber() {
//        CompletableFuture<Void> subTask = null;
//        // The publisher is closed when the try block exits
//        try (SubmissionPublisher<Long> pub = new SubmissionPublisher<>()) {
//            // Print the buffer size used for each subscriber
//            System.out.println("Subscriber Buffer Size: " + pub.getMaxBufferCapacity());
//            // Add a subscriber to the publisher. The subscriber prints the published elements
//            subTask = pub.consume(System.out::println);
//            // Generate and publish five integers
//            LongStream.range(1L, 6L)
//                    .forEach(pub::submit);
//        }
//        if (subTask != null) {
//            try {
//                // Wait until the subscriber is complete
//                subTask.get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void println(String str) {
        System.out.println(str);
    }

    private static String getDate(String style) {
        return new SimpleDateFormat(style).format(new Date());
    }


}
