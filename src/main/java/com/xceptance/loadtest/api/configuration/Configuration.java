package com.xceptance.loadtest.api.configuration;

import java.text.MessageFormat;

import org.junit.Assert;

import com.xceptance.loadtest.api.configuration.annotations.EnumProperty;
import com.xceptance.loadtest.api.configuration.annotations.Property;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.data.Address;
import com.xceptance.loadtest.api.data.CreditCard;
import com.xceptance.loadtest.api.data.PaymentLimitations;

/**
 * Test suite configuration
 */
public class Configuration
{
    /**
     * The name of the current running TestCase' class
     */
    public LTProperties properties;

    // ===============================================================
    // General

    @Property(key = "general.url")
    public String siteUrlHomepage;

    // Basic authentication username
    @Property(key = "general.credentials.username", required = false)
    public String credentialsUserName;

    // Basic authentication password
    @Property(key = "general.credentials.password", required = false)
    public String credentialsPassword;

    // URL exclude filter patterns
    @Property(key = "com.xceptance.xlt.http.filter.exclude", required = false, fallback = "")
    public ConfigList excludeUrlPatterns;

    // URL include filter patterns
    @Property(key = "com.xceptance.xlt.http.filter.include", required = false, fallback = "")
    public ConfigList includeUrlPatterns;

    // Whether or not to make the analytics calls
    @Property(key = "general.load.analytics", fallback = "true")
    public boolean loadAnalytics;

    // Whether or not to make the Resources-Load calls
    @Property(key = "general.load.appresources", fallback = "false")
    public boolean loadAppResources;

    // Whether or not to make the ltk tracking calls
    @Property(key = "general.load.ltkTracking", fallback = "false")
    public boolean loadLtkTracking;

    // URL: To start with the direct order scenario
    @Property(key = "general.direct.order.url")
    public String directOrderUrl;

    // ================================================================
    // Email
    
    // Email domain
    @Property(key = "general.email.domain")
    public String emailDomain;

    @Property(key = "general.email.localpart.prefix")
    public String emailLocalPartPrefix;

    @Property(key = "general.email.localpart.length")
    public int emailLocalPartLength;

    // Request gzipped resources
    @Property(key = "com.xceptance.xlt.http.gzip")
    public boolean applyHeaderGzip;

    // Dump long running request sessions
    @Property(key = "com.xceptance.xlt.output2disk.onResponseTime.largerThan", required = false, fallback = "0")
    public int dumpResponseTimesWhenLargerThan;

    // Long running request sessions threshold in milliseconds 
    @Property(key = "general.request.longrunning.sessionFlag.threshold", required = false, fallback = "0")
    public int longRunningRequestThresholdForSessionMarking;

    // Optional additional debug UUID in user agent
    // TODO naming
    @Property(key = "general.execute.ontimeout.retry.max")
    public int onTimeoutRetryCount;
    
    // Puts additional actions into the result browser
    @Property(key = "general.debug.actions")
    public boolean useDebugActions;

    // Allow the use of debug urls while developing
    @Property(key = "general.debug.urls")
    public boolean useDebugUrls;

    // Generate totally random emails by using the UUID generator or use the XltRandom generator to create a reproducible stream of emails
    @Property(key = "general.email.stronglyRandom")
    public boolean stronglyRandomEmails;

    // ================================================================
    // URL Filter

    @EnumProperty(key = "filter.product.url", clazz = String.class, from = 0, to = 100, stopOnGap = false, required = false)
    public EnumConfigList<String> filterProductUrls;

    @EnumProperty(key = "filter.category.url", clazz = String.class, from = 0, to = 100, stopOnGap = false, required = false)
    public EnumConfigList<String> filterCategoryUrls;

    // ================================================================
    // Search

    // Range of product searches
    @Property(key = "search.count", immutable = false)
    public ConfigRange searchesCount;

    // File with search terms
    @Property(key = "search.hitTermsFile")
    public String searchHitTermsFile;

    // Probability to execute a 'no-hits' search
    @Property(key = "search.noHits", immutable = false)
    public ConfigProbability searchNoHitsProbability;

    // A list of search params which should result in a no hits page.
    @EnumProperty(key = "search.noHitsTerms", clazz = String.class, from = 0, to = 100, stopOnGap = false, required = false, immutable = false)
    public EnumConfigList<String> searchNoHitsTerms;

    // Shall we try to bypass pagecache for searches
    @Property(key = "search.cacheBusting")
    public boolean searchCacheBusting;

    // How many different variations of the dynamic part do we want?
    @Property(key = "search.cacheBusting.count")
    public int searchCacheBustingCount;

    // Probability to execute an article search
    @Property(key = "search.deriveNewPhrases", immutable = false)
    public ConfigProbability searchNewPhraseDeriveProbability;

    // ==========================================================
    // Browsing
    
    // How often do we want to walk the catalog path from the top
    @Property(key = "browsing.flow")
    public ConfigRange fullBrowseFlow;

    // How often do we need the categories touched per browsing flow before refining
    @Property(key = "browsing.flow.categories.flow")
    public ConfigRange browseCategoriesFlow;

    // HHow often do we refine within the larger browse flow per round
    @Property(key = "browsing.flow.refine.flow")
    public ConfigRange browseRefineFlow;

    // Top category browsing probability
    @Property(key = "browsing.category.top", immutable = false)
    public ConfigProbability topCategoryBrowsing;

    // Probability for display more
    @Property(key = "browsing.displaymore", immutable = false)
    public ConfigProbability displayMoreProbability;

    // Minimum number of products to view when viewing product details
    @Property(key = "browsing.product.view.count", immutable = false)
    public ConfigRange productViewCount;

    // ===========================================================
    // Cart

    // Probability to execute a 'search' instead of using the navigation menu.
    @Property(key = "cart.search", immutable = false)
    public ConfigProbability searchOnAddToCartProbability;

    // Add to cart as distribution
    @Property(key = "cart.add.count")
    public ConfigDistribution addToCartCount;

    // How often should the cart be shown after an add to cart
    @Property(key = "cart.view", immutable = false)
    public ConfigProbability viewCartProbability;

    // Do we need a counter for add2cart and view cart, mainly for performance
    // debugging
    @Property(key = "cart.report.bySize")
    public boolean reportCartBySize;

    // how many product do we want per add to cart?
    @Property(key = "cart.product.quantity", immutable = false)
    public ConfigRange cartProductQuantity;

    // =========================================================
    // Account

    // where should we take it from
    @Property(key = "account.source", required = true)
    public String accountSource;

    // A list of accounts
    @EnumProperty(key = "account", clazz = Account.class, from = 0, to = 20, stopOnGap = false)
    public EnumConfigList<Account> accounts;

    // A list of addresses to select from, this can grown if needed
    @EnumProperty(key = "addresses", clazz = Address.class, from = 0, to = 20, stopOnGap = false)
    public EnumConfigList<Address> addresses;

    @Property(key = "account.predefined.file", required = false)
    public String predefinedAccountsFile;

    // ================================================================
    // Account Pool

    // Whether or not to separate account pools
    @Property(key = "account.pool.separator")
    public String accountPoolSiteSeparator;

    // Pools size
    @Property(key = "account.pool.size")
    public int accountPoolSize;

    // Probability to reuse an account
    // (aka, everything else does not make it to the pool)
    @Property(key = "account.pool.reuse")
    public ConfigProbability accountPoolReuseProbability;

    // ===========================================================
    // Payment

    // Credit card definitions
    @EnumProperty(key = "creditcards", clazz = CreditCard.class, from = 0, to = 100, stopOnGap = true)
    public EnumConfigList<CreditCard> creditcards;

    // Payment limitations
    @EnumProperty(key = "paymentlimitations", clazz = PaymentLimitations.class, from = 0, to = 10, stopOnGap = true)
    public EnumConfigList<PaymentLimitations> paymentLimitations;

    // ===========================================================
    // All data files to be used... this is all for sites aka with hierarchy lookup
    
    // Data file first names
    @Property(key = "data.file.firstNames")
    public String dataFileFirstNames;

    // Data file last names
    @Property(key = "data.file.lastNames")
    public String dataFileLastNames;

    /**
     * Return text from the localization section, fails if the text is not available
     *
     * @param key
     *            the key to search for including hierarchy excluding "localization."
     * @return the found localized
     */
    public String localizedText(final String key)
    {
        final String result = properties.getProperty("localization." + key);
        if (result == null)
        {
            // no result, we fail to be safe for the setup of tests
            Assert.fail(MessageFormat.format("Localization key {0} not found", key));
        }

        return result;
    }

    /**
     * Returns the properties that are current for this context and the source of this
     * configuration. You can also directly access them, if you like.
     *
     * @return the property set
     */
    public LTProperties getProperties()
    {
        return properties;
    }

    /**
     * Constructor
     */
    public Configuration()
    {
    	super();
    }
}