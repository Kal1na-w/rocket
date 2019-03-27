drop table if exists oauth_client_details;
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

-- insert client details
INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types,
 authorities, access_token_validity, refresh_token_validity)
VALUES
('crmClient1', '$2a$04$S0C1.iKHVet1ThTrhyoqHuANrsxkYBzcjN0E9UAbfoTsE58DFENBW', 'read,write,trust', 'password,refresh_token',
 'ROLE_CLIENT,ROLE_TRUSTED_CLIENT', 900, 2592000);

drop table if exists oauth_client_token;
create table oauth_client_token (
  token_id VARCHAR(256),
  token LONG,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

drop table if exists oauth_access_token;
create table oauth_access_token (
                                  token_id VARCHAR(256),
                                  token LONG,
                                  authentication_id VARCHAR(256) PRIMARY KEY,
                                  user_name VARCHAR(256),
                                  client_id VARCHAR(256),
                                  authentication LONG,
                                  refresh_token VARCHAR(256)
);

drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
                                   token_id VARCHAR(256),
                                   token LONG,
                                   authentication LONG
);

drop table if exists oauth_code;
create table oauth_code (
                          code VARCHAR(256), authentication LONG
);

drop table if exists oauth_approvals;
create table oauth_approvals (
                               userId VARCHAR(256),
                               clientId VARCHAR(256),
                               scope VARCHAR(256),
                               status VARCHAR(10),
                               expiresAt TIMESTAMP,
                               lastModifiedAt DATETIME
);