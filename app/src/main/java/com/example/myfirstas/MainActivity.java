package com.example.myfirstas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ledger.lib.apps.eth.Eth;
import com.samsung.android.sdk.blockchain.CoinType;
import com.samsung.android.sdk.blockchain.ListenableFutureTask;
import com.samsung.android.sdk.blockchain.SBlockchain;
import com.samsung.android.sdk.blockchain.account.Account;
import com.samsung.android.sdk.blockchain.account.AccountManager;
import com.samsung.android.sdk.blockchain.account.ethereum.EthereumAccount;
import com.samsung.android.sdk.blockchain.coinservice.CoinNetworkInfo;
import com.samsung.android.sdk.blockchain.coinservice.CoinService;
import com.samsung.android.sdk.blockchain.coinservice.CoinServiceFactory;
import com.samsung.android.sdk.blockchain.coinservice.ethereum.EthereumService;
import com.samsung.android.sdk.blockchain.exception.AccountException;
import com.samsung.android.sdk.blockchain.exception.RemoteClientException;
import com.samsung.android.sdk.blockchain.exception.RootSeedChangedException;
import com.samsung.android.sdk.blockchain.exception.SsdkUnsupportedException;
import com.samsung.android.sdk.blockchain.network.EthereumNetworkType;
import com.samsung.android.sdk.blockchain.ui.CucumberWebView;
import com.samsung.android.sdk.blockchain.ui.OnSendTransactionListener;
import com.samsung.android.sdk.blockchain.wallet.HardwareWallet;
import com.samsung.android.sdk.blockchain.wallet.HardwareWalletType;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.web3j.abi.datatypes.Int;

import java.math.BigInteger;
import java.net.CookiePolicy;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import jnr.ffi.annotations.In;



public class MainActivity extends AppCompatActivity implements OnSendTransactionListener {




    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            // 1초 뒤 다음 하면 넘어가기
            Intent intent = new Intent(getApplicationContext(), FirstPage.class);
            startActivity(intent); // 다음 화면 넘어가기
            finish();
        }
    };




    Button nextpageBtn;
    Button FirstPageBtn;
    Button connectBtn;
    Button paymentBtn;

    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    Button checkBtn;
    TextView checkResult;

    Spinner yearSpinner;
    Spinner monthSpinner;


    SBlockchain sBlockchain = new SBlockchain();
    private HardwareWallet wallet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main); // xml 과 java 소스를 연
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        sBlockchain = new SBlockchain();
        try {
            sBlockchain.initialize(this);

        } catch (SsdkUnsupportedException e) {
            e.printStackTrace();
        }

        nextpageBtn = findViewById(R.id.nextpage);
        FirstPageBtn = findViewById(R.id.FirstPage);


        // 체크 박스 정의
        cb1 = (CheckBox) findViewById(R.id.checkBox1);
        cb2 = (CheckBox) findViewById(R.id.checkBox2);
        cb3 = (CheckBox) findViewById(R.id.checkBox3);
        cb4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBtn = findViewById(R.id.checkbtn);
        checkResult = findViewById(R.id.checkresult);


        // 지갑의 사용을 위한 connectBtn
        connectBtn = findViewById(R.id.connect);


        // year Spinner 정의
        yearSpinner = (Spinner)findViewById(R.id.spinner_year);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this, R.array.date_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        yearSpinner.setAdapter(yearAdapter);

        // month Spinner 정의
        monthSpinner = (Spinner)findViewById(R.id.spinner_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.date_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        monthSpinner.setAdapter(monthAdapter);



        //generateAccountBtn = findViewById(R.id.generateAccount);
        paymentBtn = findViewById(R.id.payment);


        nextpageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), subtitle.class);
                startActivity(intent);
            }
        });

        FirstPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FirstPage.class);
                startActivity(intent);
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result =  ""; //결과를 출력할 문자열 , 항상 스트링을 빈문자열로 초기화할 것
                if (cb1.isChecked() == true) result += cb1.getText().toString();
                if (cb2.isChecked() == true) result += cb2.getText().toString();
                if (cb3.isChecked() == true) result += cb3.getText().toString();
                if (cb4.isChecked() == true) result += cb4.getText().toString();
                checkResult.setText("선택결과 : " + result);

            } // end onClick
        }); // end setOnClickListener


        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });


        paymentBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                payment();
            }
        });



    }


    @Override
    protected void onResume(){
        super.onResume();
        // 다시 화면에 들어왔을 때 예약 걸어주기
        handler.postDelayed(r, 1000); // 1초 뒤에 Runnable 객체 수
    }

    @Override
    protected void onPause(){
        super.onPause();
        // 화면을 벗어나면, handler 에 예약해놓은 작업 취소.
    }



    private void payment(){
        CoinNetworkInfo coinNetworkInfo = new CoinNetworkInfo(
                CoinType.ETH,
                EthereumNetworkType.ROPSTEN,
                "https://ropsten.infura.io/v3/c390e494bf26472ab6cf48f14be05495"
        );
        List<Account> accounts = sBlockchain.getAccountManager()
                .getAccounts(
                        wallet.getWalletId(),
                        CoinType.ETH,
                        EthereumNetworkType.ROPSTEN
                );

        EthereumService service = (EthereumService) CoinServiceFactory.getCoinService(  getApplicationContext(), coinNetworkInfo);
        Intent intent = service
                 .createEthereumPaymentSheetActivityIntent(
                         this,
                         wallet,
                         (EthereumAccount) accounts.get(0),
                         "0x0f63c51Ff21FABF34d796acF3F074B5592DD9F19",
                         new BigInteger("1000000000000000"),
                         null,
                         null


                 );

        startActivityForResult(intent,0);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    private void connect(){
        sBlockchain.getHardwareWalletManager()
                .connect(HardwareWalletType.SAMSUNG,true)
                .setCallback(new ListenableFutureTask.Callback<HardwareWallet>() {
                    @Override
                    public void onSuccess(HardwareWallet hardwareWallet) {
                        wallet = hardwareWallet;
                    }

                    @Override
                    public void onFailure(@NotNull ExecutionException e) {

                    }

                    @Override
                    public void onCancelled(@NotNull InterruptedException e) {

                    }
                });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSendTransaction(@NotNull String s,
                                  @NotNull EthereumAccount ethereumAccount,
                                  @NotNull String s1,
                                  @org.jetbrains.annotations.Nullable BigInteger bigInteger,
                                  @org.jetbrains.annotations.Nullable String s2,
                                  @org.jetbrains.annotations.Nullable BigInteger bigInteger1) {

    }
}
