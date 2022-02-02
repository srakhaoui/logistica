package com.logistica.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.logistica.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.logistica.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.logistica.domain.User.class.getName());
            createCache(cm, com.logistica.domain.Authority.class.getName());
            createCache(cm, com.logistica.domain.User.class.getName() + ".authorities");
            createCache(cm, com.logistica.domain.Societe.class.getName());
            createCache(cm, com.logistica.domain.Transporteur.class.getName());
            createCache(cm, com.logistica.domain.Fournisseur.class.getName());
            createCache(cm, com.logistica.domain.Produit.class.getName());
            createCache(cm, com.logistica.domain.Client.class.getName());
            createCache(cm, com.logistica.domain.Trajet.class.getName());
            createCache(cm, com.logistica.domain.TarifVente.class.getName());
            createCache(cm, com.logistica.domain.TarifAchat.class.getName());
            createCache(cm, com.logistica.domain.TarifTransport.class.getName());
            createCache(cm, com.logistica.domain.Livraison.class.getName());
            createCache(cm, com.logistica.domain.Gasoil.class.getName());
            createCache(cm, com.logistica.domain.GasoilAchatGros.class.getName());
            createCache(cm, com.logistica.domain.ClientGrossiste.class.getName());
            createCache(cm, com.logistica.domain.GasoilVenteGros.class.getName());
            createCache(cm, com.logistica.domain.Carburant.class.getName());
            createCache(cm, com.logistica.domain.FournisseurGrossiste.class.getName());
            createCache(cm, com.logistica.domain.Depot.class.getName());
            createCache(cm, com.logistica.domain.GasoilTransfert.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
