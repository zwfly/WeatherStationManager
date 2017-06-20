package com.yurunsd.weatherstationmanager.utils;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MqttUtils {

    private MqttClient client;
    private MqttConnectOptions options;

    public MqttConnectOptions getOptions() {
        return options;
    }


    public MqttUtils(String host, String clientid, String userName, String password,
                     MqttClientPersistence persistence) {
        try {
            // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, clientid, persistence);
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            options.setServerURIs(new String[]{host});
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(password.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(5);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void reconnect() {
        try {
            client.reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void connect() throws MqttException {
        client.connect(options);
    }

    public void close() {
        try {
            if (client.isConnected()) {
                client.disconnect();
            }
            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic, int qos) throws MqttException {
        client.subscribe(topic, qos);

    }

    /*
        public void subscribe(final String topic, final int qos) {

            RunnableUtil run = new RunnableUtil() {
                @Override
                public void run() {
                    int cnt = 0;
                    while (true) {
                        if (client == null) {
                            return;
                        }
                        if (client.isConnected()) {
                            boolean success = false;
                            try {
                                client.subscribe(topic, qos);
                                System.out.println("mqtt client subscribe success");
                                success = true;
                            } catch (MqttException e) {
                                e.printStackTrace();
                                System.out.println("mqtt client subscribe MqttException");
                                success = false;
                            }
                            if (success) {
                                return;
                            }
                        } else {
                            try {
                                client.connect();
                            } catch (MqttException e) {
                                e.printStackTrace();
                                return;
                            }
                            System.out.println("mqtt client subscribe cnt:" + cnt);
                            try {
                                Thread.sleep(150);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        cnt++;
                        if (cnt > 500) {
                            System.out.println("mqtt client subscribe timeout and fail");
                            return;
                        }
                    }

                }
            };

            new Thread(run).start();

        }
    */
    public void subscribe(String[] ntopic, int[] nqos) {
        try {
            client.subscribe(ntopic, nqos);//订阅接主题
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void unsubscribe(String topic) {
        try {
            client.unsubscribe(topic);//订阅接主题
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void publish(MqttMessage msg, String topic) {
        if (client != null) {
            if (client.isConnected()) {
                try {
                    client.publish(topic, msg);
                    Log.d("publish", "topic " + topic + ", msg: " + msg);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 简单回调函数，处理client接收到的主题消息
     *
     * @author pig
     */
    private class mqttCallback implements MqttCallbackExtended {


        @Override
        public void connectComplete(boolean b, String s) {
            System.out.println("connectComplete");
        }

        @Override
        public void connectionLost(Throwable throwable) {
            System.out.println("connectionLost");
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

            System.out.println("messageArrived " + s + " --- " + mqttMessage);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            System.out.println("deliveryComplete");
        }
    }


}