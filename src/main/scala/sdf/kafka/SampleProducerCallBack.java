package sdf.kafka;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @Author defei.su
 * @Date 2020/10/9 14:51
 */
public class SampleProducerCallBack implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if( e != null){
            e.printStackTrace();
        }

    }
}
