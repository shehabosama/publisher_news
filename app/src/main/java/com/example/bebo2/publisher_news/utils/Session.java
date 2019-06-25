package com.example.bebo2.publisher_news.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.bebo2.publisher_news.Login_Activity;
import com.example.bebo2.publisher_news.models.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Session {
    private static Session instance;
    Realm realm;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    private Session() {
        RealmConfiguration realmConf = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConf);
    }

    public void loginUser(final User user) {
        //  User u = realm.where(User.class).equalTo("username","shehab").findFirst();
        //   List<User> users = realm.where(User.class).equalTo("username","osama").findAll();
        if (realm.where(User.class).findFirst() == null) {
            final RealmResults<User> realmResults = realm.where(User.class).equalTo("username", "shehab").findAll();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(user);
                    // u.username = "fathi";
                    //u.deleteFromRealm();
                    //realmResults.deleteAllFromRealm();


                }
            });
        } else {
            logout();
            loginUser(user);
        }

    }

    private void logout() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(User.class);
            }
        });
    }

    public boolean isUserLoggedin() {
        if (realm.where(User.class).findFirst() != null) {
            return true;
        } else

        {
            return false;
        }
    }

    public User getUser()
    {
        return realm.where(User.class).findFirst();
    }

    public void logoutAndGoTologin(Activity activity)
    {
        logout();
        activity.startActivity(new Intent(activity,Login_Activity.class));
        activity.finish();

    }
}
