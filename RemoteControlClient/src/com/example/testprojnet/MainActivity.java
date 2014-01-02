package com.example.testprojnet;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
 
public class MainActivity extends Activity
{
    private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdapter mAdapter;
    private TCPClient mTcpClient;
    private boolean touched = false;
    private String ip;
    
    JoystickView joystick;
    TextView txtX, txtY;
 
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Bundle b = getIntent().getExtras();
        ip = b.getString("ip");
 
        Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_SHORT).show();
        
        arrayList = new ArrayList<String>();
 
        /*final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button)findViewById(R.id.send_button);*/
        Button hello = (Button)findViewById(R.id.hello_button);
        Button connect = (Button)findViewById(R.id.connect_button);
        Button close = (Button)findViewById(R.id.close_button);
        Button data2 = (Button)findViewById(R.id.data2_button);
        
        Button up = (Button)findViewById(R.id.up);
        Button down = (Button)findViewById(R.id.down);
        Button left = (Button)findViewById(R.id.left);
        Button right = (Button)findViewById(R.id.right);
        
        /*txtX = (TextView)findViewById(R.id.textViewX);
        txtY = (TextView)findViewById(R.id.textViewY);*/
        
        LinearLayout ll = (LinearLayout) findViewById(R.id.joystick);
        joystick = new JoystickView(getApplicationContext());
        joystick.setLayoutParams(new LayoutParams(300,300));
        joystick.setMovementConstraint(1);
        joystick.setMovementRange(10);
        
        ll.addView(joystick);
        
        joystick.setOnJostickMovedListener(new JoystickMovedListener() {
			
        	@Override
            public void OnMoved(int pan, int tilt) {
                    //txtX.setText(Integer.toString(pan));
                    //txtY.setText(Integer.toString(tilt));
                    String message = "";
                    if(tilt>0){
                    	message = "<message type=\"data\" id=\"1\" port=\"1\"><data>1</data></message>";
                    } else if(tilt<0){
                    	message = "<message type=\"data\" id=\"1\" port=\"2\"><data>2</data></message>";
                    }
                    if (message.compareTo("")!=0) {
                        arrayList.add("c: " + message);
                        if (mTcpClient != null) {
                            mTcpClient.sendMessage(message);
                        }
                    }
                    
                    mAdapter.notifyDataSetChanged();
                    if(pan>0){
                    	message = "<message type=\"data\" id=\"1\" port=\"3\"><data>3</data></message>";
                    } else if(pan<0){
                    	message = "<message type=\"data\" id=\"1\" port=\"4\"><data>4</data></message>";
                    }
                    if (message.compareTo("")!=0) {
                        arrayList.add("c: " + message);
                        if (mTcpClient != null) {
                            mTcpClient.sendMessage(message);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnReleased() {
                    /*txtX.setText("released");
                    txtY.setText("released");*/
            }
            
            public void OnReturnedToCenter() {
                    /*txtX.setText("stopped");
                    txtY.setText("stopped");*/
            };
		});
        
        //relate the listView from java to the one created in xml
        mList = (ListView)findViewById(R.id.list);
        mAdapter = new MyCustomAdapter(this, arrayList);
        mList.setAdapter(mAdapter);
 
        // connect to the server
        new connectTask().execute("");
 
        /*send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                String message = editText.getText().toString();
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });*/
        
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                String message = "<message type=\"hello\"></message>";
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
            }
        });
        
        
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                String message = "<message type=\"connect\"><data><device>Nexus 7</device></data></message>";
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
            }
        });
        
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                String message = "<message type=\"close\" id=\"1\"></message>";
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
            }
        });
        
        data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
            	String message = "<message type=\"data\" id=\"\" port=\"5\"><data>5</data></message>";
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
            }
        });
        
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
            	String message = "<message type=\"data\" id=\"\" port=\"1\"><data></data></message>";
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
            }
        });
        
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
            	String message = "<message type=\"data\" id=\"\" port=\"2\"><data></data></message>";
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
            }
        });
        
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
            	String message = "<message type=\"data\" id=\"\" port=\"3\"><data></data></message>";
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
            }
        });
        
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
            	String message = "<message type=\"data\" id=\"\" port=\"4\"><data></data></message>";
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
            }
        });
 
    }
 
    public class connectTask extends AsyncTask<String,String,TCPClient> {
 
        @Override
        protected TCPClient doInBackground(String... message) {
 
            //we create a TCPClient object and
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            },"192.168.1.6");
            mTcpClient.run();
 
            return null;
        }
 
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
 
            //in the arrayList we add the messaged received from server
            arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();
        }
    }
}
