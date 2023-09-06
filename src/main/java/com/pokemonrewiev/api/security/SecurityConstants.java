package com.pokemonrewiev.api.security;

import org.springframework.web.bind.annotation.CrossOrigin;

public class SecurityConstants {
    public static final long JWT_EXPIRATION=1500000; //10 dakika
    public static final long REFRESH_EXPIRATION=1200000; //20 dakika
    public static final String JWT_SECRET="RzCjThUaXwzvyyBEHMBeShVmYqtwzzCHEHMcfTjWnZruxADGKaPdSgVkYpsvyyBEHM";
}
