package veera.chat.com.chatbot.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public abstract class MVPBaseActivity<P extends MVPBasePresenter> extends AppCompatActivity implements MVPBaseView<P> {

    private P presenter;
    boolean isDestroyed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getNewPresenter();
        getPresenter().bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        getPresenter().unBindView();
    }

    @Override
    public boolean isAlive() {
        return !isFinishing() && !isDestroyed;
    }

    public final P getPresenter() {
        return presenter;
    }
}
