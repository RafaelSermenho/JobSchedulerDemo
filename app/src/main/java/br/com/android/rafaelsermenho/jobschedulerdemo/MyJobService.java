package br.com.android.rafaelsermenho.jobschedulerdemo;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

public class MyJobService extends JobService {

    private static final String TAG = MyJobService.class.getSimpleName();
    private HelloWorldAsync mHelloWorldAsync = null;

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.i(TAG, "onStartJob");
        mHelloWorldAsync = new HelloWorldAsync() {
            @Override
            protected void onPostExecute(Boolean success) {
                Log.i(TAG, "onPostExecute: success? " + success);
                super.onPostExecute(success);
                jobFinished(params, !success);
            }
        };
        mHelloWorldAsync.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        if (mHelloWorldAsync != null) {
            mHelloWorldAsync.cancel(true);
        }
        return true;
    }

    private static class HelloWorldAsync extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(TAG, "Hello World");
            return true;
        }

    }
}