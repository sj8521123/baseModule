package basemodule.sj.com.basic.common;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Twan on 2017/2/12.
 */

public class InitializeService extends IntentService {

    private static final String ACTION_INIT = "initApplication";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    private void initApplication() {

        //初始化内存泄漏检测
       /* LeakCanary.install(App.getInstance());

        new CrashHandler(App.getInstance()).init();*/
    }


}
