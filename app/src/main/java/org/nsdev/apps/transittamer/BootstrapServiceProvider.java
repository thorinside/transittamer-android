
package org.nsdev.apps.transittamer;

import android.accounts.AccountsException;

import org.nsdev.apps.transittamer.authenticator.ApiKeyProvider;
import org.nsdev.apps.transittamer.core.BootstrapService;
import org.nsdev.apps.transittamer.core.UserAgentProvider;
import com.google.inject.Inject;

import java.io.IOException;

/**
 * Provider for a {@link org.nsdev.apps.transittamer.core.BootstrapService} instance
 */
public class BootstrapServiceProvider {

    @Inject private ApiKeyProvider keyProvider;
    @Inject private UserAgentProvider userAgentProvider;

    /**
     * Get service for configured key provider
     * <p>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    /*
    public BootstrapService getService() throws IOException, AccountsException {
        return new BootstrapService(keyProvider.getAuthKey(), userAgentProvider);
    }
    */
}
