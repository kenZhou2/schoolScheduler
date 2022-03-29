package com.example.schoolscheduler;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void hash_isCorrect(){
        String testing1 = "Random22";

        byte[] hashPW = testing1.getBytes();
        BigInteger hashData = null;
        try{
            hashData = new BigInteger(1, hashMD5.hash(hashPW));
        }
        catch(Exception i){
            i.printStackTrace();
        }
        String hashed = hashData.toString();

        assertNotEquals(testing1,hashed);
    }


}