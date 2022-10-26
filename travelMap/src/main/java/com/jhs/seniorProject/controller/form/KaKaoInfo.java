package com.jhs.seniorProject.controller.form;

import lombok.Data;

@Data
public class KaKaoInfo {
    public Long id;
    public String connectedAt;
    public Properties properties;
    public KaKaoAccount kakaoAccount;

    @Data
    public class KaKaoAccount {
        public Boolean profileNicknameNeedsAgreement;
        public Profile profile;
        public Boolean hasEmail;
        public Boolean emailNeedsAgreement;
        public Boolean isEmailValid;
        public Boolean isEmailVerified;
        public String email;

        @Data
        public class Profile {
            public String nickname;
        }
    }

    @Data
    public class Properties {
        public String nickname;
    }
}



