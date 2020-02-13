package com.example.myfirstas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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


public class WaitRoom extends AppCompatActivity implements OnSendTransactionListener {

    Button paymentBtn;
    Button connectgdragonBtn;

    SBlockchain sBlockchain = new SBlockchain();
    private HardwareWallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_room);
        Toolbar toolbar =findViewById(R.id.toolbar);

        sBlockchain = new SBlockchain();
        try {
            sBlockchain.initialize(this);

        } catch (SsdkUnsupportedException e) {
            e.printStackTrace();
        }

        // 결제 UI 발생
        paymentBtn = findViewById(R.id.ready);

        // 지드래곤 닉네임 터치 시 지갑 연동
        connectgdragonBtn =findViewById(R.id.connect_gdraagon);

        connectgdragonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect_gdragon();
            }
        });


        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready();
            }
        });

    }
    private void connect_gdragon(){
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

    private void ready(){
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
    public void onSendTransaction(@NotNull String s,
                                  @NotNull EthereumAccount ethereumAccount,
                                  @NotNull String s1,
                                  @org.jetbrains.annotations.Nullable BigInteger bigInteger,
                                  @org.jetbrains.annotations.Nullable String s2,
                                  @org.jetbrains.annotations.Nullable BigInteger bigInteger1) {

    }

}
