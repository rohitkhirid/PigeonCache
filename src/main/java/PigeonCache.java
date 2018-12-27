import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.UUID;

public class PigeonCache {
    private static PigeonCache sInstance = null;
    private HashMap<String, WeakReference<Object>> sCache = null;

    private PigeonCache() {
        sCache = new HashMap<String, WeakReference<Object>>();
    }

    /**
     * get a newly created or older instance of cache
     *
     * @return instance of {@link PigeonCache} to work with
     */
    @NotNull
    public static PigeonCache getInstance() {
        if (sInstance == null) {
            sInstance = new PigeonCache();
        }
        return sInstance;
    }

    /**
     * @param obj : Object which is to be stored in Cache.
     * @return key associated with obj, null if obj is null
     * use this key to pass in intents, bundles or any place to fetch the stored object
     * @see {{@link PigeonCache#put(String, Object)}}
     */
    @Nullable
    public String put(Object obj) {
        return put(UUID.randomUUID().toString(), obj);
    }

    /**
     * @param obj  : Object which is to be stored in Cache.
     * @param key: key with which you want to store the object
     * @return key associated with obj, null if obj is null
     * use this key to pass in intents, bundles or any place to fetch the stored object
     * @see {{@link PigeonCache#put(Object)}}
     */
    @Nullable
    public String put(String key, Object obj) {
        if (obj == null) {
            return null;
        }

        if (!isKeyValid(key)) {
            return null;
        }

        sCache.put(key, new WeakReference<Object>(obj));
        return key;
    }

    /**
     * @param type : Type of obj you are looking for (ex String.class, User.class, etc)
     * @param key: key returned when you inserted the object in {@link PigeonCache#put(Object)} or
     *             {@link PigeonCache#put(String, Object)}
     * @return key associated with obj, null if obj is null
     */
    @Nullable
    public <T> T get(Class<T> type, String key) {
        if (!isKeyValid(key)) {
            return null;
        }

        if (sCache.containsKey(key)) {
            WeakReference<Object> objectWeakReference = sCache.get(key);
            if (objectWeakReference != null) {
                Object possiblyObjectOfT = objectWeakReference.get();
                if (type.isInstance(possiblyObjectOfT)) {
                    return (T) possiblyObjectOfT;
                }
            }
        }

        return null;
    }

    /**
     * @param key
     * @return {@link Boolean#TRUE} is key is non-null and non-empty
     */
    private boolean isKeyValid(String key) {
        return key != null && key.length() != 0;
    }
}