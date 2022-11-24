package Reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();

    public static synchronized ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./target/extent-reports/extent-report.html");
        reporter.config().setReportName("Automation API");
        extentReports.attachReporter(reporter);
//        extentReports.setSystemInfo("Blog Name", "SW Test Academy");
        extentReports.setSystemInfo("Author", "Ramos");
        return extentReports;
    }
}
