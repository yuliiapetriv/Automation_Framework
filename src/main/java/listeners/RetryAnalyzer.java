package listeners;

import common.Log;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        int maxRetryCount = 2;
        if(retryCount < maxRetryCount) {
            retryCount++;
            Log.info("Retrying test " + result.getName() + " for " + retryCount + " time(s).");
            return true;
        }
        return false;
    }
}
