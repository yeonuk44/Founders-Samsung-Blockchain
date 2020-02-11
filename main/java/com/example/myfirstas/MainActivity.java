package com.example.myfirstas;

import android.content.Intent;
import android.os.Bundle;

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
import com.samsung.android.sdk.blockchain.wallet.HardwareWallet;
import com.samsung.android.sdk.blockchain.wallet.HardwareWalletType;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.net.CookiePolicy;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button connectBtn;
    Button generateAccountBtn;
    Button getAccountsBtn;
    Button paymentBtn;
    Button sendsmartcontractapiBtn;
    SBlockchain sBlockchain = new SBlockchain();
    private HardwareWallet wallet;
    private Account mAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sBlockchain = new SBlockchain();
        try {
            sBlockchain.initialize( this);

        } catch (SsdkUnsupportedException e ) {
            e.printStackTrace();
        }

        connectBtn = findViewById(R.id.connect);
        generateAccountBtn = findViewById(R.id.generateAccount);
        getAccountsBtn = findViewById(R.id.getAccount);
        paymentBtn = findViewById(R.id.payment);
        sendsmartcontractapiBtn = findViewById(R.id.sendsmartcontractapi);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

        generateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate();

            }
        });

        getAccountsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccounts();
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                payment();
            }
        });
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

    private void getAccounts(){
        List<Account> accounts = sBlockchain.getAccountManager()
                .getAccounts(wallet.getWalletId(), CoinType.ETH, EthereumNetworkType.ROPSTEN);
        Log.d("Myapp", Arrays.toString(new List[]{accounts}));

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

    private void generate(){
        CoinNetworkInfo coinNetworkInfo = new CoinNetworkInfo(
                CoinType.ETH,
                EthereumNetworkType.ROPSTEN,
                "https://ropsten.infura.io/v3/c390e494bf26472ab6cf48f14be05495"
        );

        sBlockchain.getAccountManager()
                .generateNewAccount(wallet,coinNetworkInfo)
                .setCallback(new ListenableFutureTask.Callback<Account>() {
                    @Override
                    public void onSuccess(Account account) {
                        mAccount = account;
                        Log.d("MyApp",account.toString());
                    }

                    @Override
                    public void onFailure(@NotNull ExecutionException e) {
                        Log.d("MyApp",e.getMessage());
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
}
