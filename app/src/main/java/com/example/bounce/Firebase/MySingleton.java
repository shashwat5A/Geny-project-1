package com.example.bounce.Firebase;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {


        private static MySingleton mInstance;
        private static Context mCtx;
        private RequestQueue requestQueue;

    private MySingleton(Context context)
        {
            mCtx = context;
            requestQueue = getReqeustQueue();
        }

        private RequestQueue getReqeustQueue()
        {
            if(requestQueue==null)
            {
                requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
            return requestQueue;
        }

        public static synchronized MySingleton getmInstance(Context context)
        {
            if(mInstance==null)
            {
                mInstance =  new MySingleton(context);
            }
            return mInstance;
        }

        public<T> void addToRequestQueue(Request<T> request)
        {
            getReqeustQueue().add(request);
        }
}
