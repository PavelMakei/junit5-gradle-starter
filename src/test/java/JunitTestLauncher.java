import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class JunitTestLauncher {
    public static void main(String[] args) {
        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();

        Launcher launcher = LauncherFactory.create();
//        launcher.registerTestExecutionListeners(summaryGeneratingListener);
//        launcher.registerLauncherDiscoveryListeners();


        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
//                .selectors(DiscoverySelectors.selectClass(UserServiceTest.class))
                .selectors(DiscoverySelectors.selectPackage("by.makei.junit.service"))
                .build();
        launcher.execute(request, summaryGeneratingListener);

        try (PrintWriter writer = new PrintWriter(System.out)) {
            summaryGeneratingListener.getSummary().printTo(writer);
        }

    }
}
