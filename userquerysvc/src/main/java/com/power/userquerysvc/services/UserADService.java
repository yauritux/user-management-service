package com.power.userquerysvc.services;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.requests.GraphServiceClient;
import com.power.userquerysvc.projections.DefaultUserView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserADService {

    @Value("${spring.security.oauth2.client.registration.azure.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.azure.client-secret}")
    private String clientSecret;

    @Value("${azure.activedirectory.tenant-id}")
    private String tenantId;

    public com.microsoft.graph.models.User createUser(DefaultUserView user) {
        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(
                List.of("https://graph.microsoft.com/.default"), clientSecretCredential);

        final GraphServiceClient graphServiceClient =
                GraphServiceClient.builder()
                .authenticationProvider(tokenCredentialAuthProvider)
                .buildClient();

        com.microsoft.graph.models.User adUser = new com.microsoft.graph.models.User();
        adUser.accountEnabled = true;
        adUser.displayName = user.getFirstName() + " " + user.getLastName();
        adUser.mailNickname = user.getFirstName();
        adUser.userPrincipalName = user.getUsername() + "@yauritux.onmicrosoft.com";
        adUser.mail = user.getEmail();
        adUser.surname = user.getFirstName();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PasswordProfile passwordProfile = new PasswordProfile();
        passwordProfile.forceChangePasswordNextSignIn = false;
        passwordProfile.password = passwordEncoder.encode(user.getPassword());
        adUser.passwordProfile = passwordProfile;
        graphServiceClient.users().buildRequest().post(adUser);
        return adUser;
    }
}
