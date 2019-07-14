package project.view;

import java.util.ResourceBundle;

public enum FxmlView {

    HOME {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("home.title");
        }

        @Override
        public String getFxmlFile() {
            return "../fxml/home.fxml";
        }
    },
    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "../fxml/login.fxml";
        }
    },
    SIGNUP {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("signup.title");
        }

        @Override
        public String getFxmlFile() {
            return "../fxml/signup.fxml";
        }
    },
    TRASPORTATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("transportation.title");
        }

        @Override
        public String getFxmlFile() {
            return "../fxml/Transportation.fxml";
        }
    },
    PROFILE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("profile.title");
        }

        @Override
        public String getFxmlFile() {
            return "../fxml/profile.fxml";
        }
    },
    LEADERBOARD {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("leaderboard.title");
        }

        @Override
        public String getFxmlFile() {
            return "../fxml/leaderboard.fxml";
        }
    },
    SOLARPANELFORM {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("solarPanelForm.title");
        }

        @Override
        public String getFxmlFile() {
            return "../fxml/solarPanelForm.fxml";
        }
    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
