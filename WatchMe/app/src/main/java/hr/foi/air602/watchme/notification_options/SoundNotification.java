package hr.foi.air602.watchme.notification_options;

import hr.foi.air602.notification.essentials.NotificationOptions;

/**
 * Created by Goran on 26.1.2017..
 */

public class SoundNotification implements NotificationOptions {

    @Override
    public boolean getSound() {
        return true;
    }

    @Override
    public boolean getVibration() {
        return false;
    }
}