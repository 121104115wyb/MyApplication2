package com.ytlz.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by INTG-02 on 2018/3/21.
 */

public class PersonalInfoDatabaseHelper extends BaseDatabaseHelper {
    private static final String TAG = "InfoDatabaseHelper";
    private static PersonalInfoDatabaseHelper personalInfoDatabaseHelper;

    public synchronized static PersonalInfoDatabaseHelper getInstance(Context context) {
        if (personalInfoDatabaseHelper == null) {
            personalInfoDatabaseHelper = new PersonalInfoDatabaseHelper(context);
        }
        return personalInfoDatabaseHelper;
    }

    public PersonalInfoDatabaseHelper(@NonNull Context context) {
        super(context);
    }


    public void addPersio(PersonalInfoData infoData) {
        Long row = null;
        try {
            row = cupboard().withDatabase(getWritableDatabase()).put(infoData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectPersio() {
        QueryResultIterable<PersonalInfoData> iter  = cupboard()
                .withDatabase(getReadableDatabase())
                .query(PersonalInfoData.class)
                .withSelection("name='wyb'")
                .query();
        PersonalInfoData(iter);
        Log.d(TAG, "selectPersio: ---------"+iter);

    }

    private static List<PersonalInfoData> PersonalInfoData(QueryResultIterable<PersonalInfoData> iter) {

        final List<PersonalInfoData> bunnies = new ArrayList<PersonalInfoData>();
        for (PersonalInfoData bunny : iter) {
            bunnies.add(bunny);
        }
        iter.close();

        return bunnies;
    }


//
//    //添加用户信息
//    public Observable<PersonalInfoData> putPersonalInfoData(PersonalInfoData entity) {
//        return Observable.create(subscriber -> {
//            if (!isClosed()) {
//                Long row = null;
//                try {
//                    row = cupboard().withDatabase(getWritableDatabase()).put(entity);
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//                subscriber.onNext(entity);
//                subscriber.onComplete();
//            }
//
//        });
//    }
//
//    //获取个人信息特征字段
//    public Observable<GuestDO> getPersonalIdInfo(String LocalID) {
//        return Observable.create(subscriber -> {
//            GuestDO personalInfoData = cupboard()
//                    .withDatabase(getReadableDatabase())
//                    .query(GuestDO.class)
//                    .withSelection("guestId = ?", String.valueOf(LocalID))
//                    .get();
//            if (personalInfoData == null) personalInfoData = new GuestDO();
//
//            subscriber.onNext(personalInfoData);
//            subscriber.onComplete();
//        });
//    }
//
//    //根据名字获取个人信息特征字段
//    public Observable<List<PersonalInfoData>> getPersonalNameInfo(String name) {
//        return Observable.create(subscriber -> {
//            List<PersonalInfoData> personalInfoData = cupboard()
//                    .withDatabase(getReadableDatabase())
//                    .query(PersonalInfoData.class)
//                    .withSelection("name = ?", String.valueOf(name))
//                    .list();
//            if (personalInfoData == null) personalInfoData = new ArrayList<PersonalInfoData>();
//
//            subscriber.onNext(personalInfoData);
//            subscriber.onComplete();
//        });
//    }
//
//    //根据电话获取个人信息特征字段
//    public Observable<List<PersonalInfoData>> getPersonalphoneNumInfo(String phoneNum) {
//        return Observable.create(subscriber -> {
//            List<PersonalInfoData> personalInfoData = cupboard()
//                    .withDatabase(getReadableDatabase())
//                    .query(PersonalInfoData.class)
//                    .withSelection("phoneNum = ?", String.valueOf(phoneNum))
//                    .list();
//            if (personalInfoData == null) personalInfoData = new ArrayList<PersonalInfoData>();
//
//            subscriber.onNext(personalInfoData);
//            subscriber.onComplete();
//        });
//    }
//
//    //根据部分信息获取个人信息特征字段
//    public Observable<List<PersonalInfoData>> getPersonalportionInfo(PersonalInfoData personalInfo) {
//        return Observable.create(subscriber -> {
//            if(personalInfo.getName()==null){
//                personalInfo.setName("");
//            }
//            if(personalInfo.getAddress()==null){
//                personalInfo.setAddress("");
//            }
//            if(personalInfo.getPhoneNum()==null){
//                personalInfo.setPhoneNum("");
//            }
//            List<PersonalInfoData> personalInfoData = cupboard()
//                    .withDatabase(getReadableDatabase())
//                    .query(PersonalInfoData.class)
//                    .withSelection("(name like ?) and (address like ?) and (phoneNum like ?)", String.valueOf("%"+personalInfo.getName()+"%"), "%"+String.valueOf(personalInfo.getAddress())+"%", "%"+String.valueOf(personalInfo.getPhoneNum())+"%")
//                    .list();
//            // and (address like '%?%') and (phoneNum like '%?%')"
//            //, String.valueOf(personalInfo.getAddress()), String.valueOf(personalInfo.getPhoneNum())
//
//            if (personalInfoData == null) personalInfoData = new ArrayList<PersonalInfoData>();
//
//            subscriber.onNext(personalInfoData);
//            subscriber.onComplete();
//        });
//    }
//
//    //根据地址获取个人信息特征字段
//    public Observable<List<PersonalInfoData>> getPersonaladdressInfo(String address) {
//        return Observable.create(subscriber -> {
//            List<PersonalInfoData> personalInfoData = cupboard()
//                    .withDatabase(getReadableDatabase())
//                    .query(PersonalInfoData.class)
//                    .withSelection("address = ?", String.valueOf(address))
//                    .list();
//            if (personalInfoData == null) personalInfoData = new ArrayList<PersonalInfoData>();
//
//            subscriber.onNext(personalInfoData);
//            subscriber.onComplete();
//        });
//    }
//
//    //获取所有人脸信息文件
//    public Observable<List<GuestDO>> getPersonalInfoData() {
//        return Observable.create(subscriber -> {
//            List<GuestDO> personalInfoData = cupboard()
//                    .withDatabase(getReadableDatabase())
//                    .query(GuestDO.class)
//                    .list();
//            if (personalInfoData == null) personalInfoData = new ArrayList<GuestDO>();
//
//            subscriber.onNext(personalInfoData);
//            subscriber.onComplete();
//        });
//    }
//
//
//    //删除个人信息特征字段
//    public Observable<Boolean> deletePersonalInfoData(String personId) {
//        return Observable.create(subscriber -> {
//            if (!isClosed()) {
//                try {
//                    cupboard().withDatabase(getWritableDatabase()).delete(PersonalInfoData.class, "name = ?", personId.toString());
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                    subscriber.onNext(Boolean.FALSE);
//                }
//                subscriber.onNext(Boolean.TRUE);
//                subscriber.onComplete();
//            }
//        });
//    }
//
//
//    //添加用户信息
//    public Observable<BaseResponse> putUserRegisterData(UserRegisterQuest quest) {
//        return Observable.create(new ObservableOnSubscribe<BaseResponse>() {
//            @Override
//            public void subscribe(ObservableEmitter<BaseResponse> emitter) throws Exception {
//                if (!isClosed()){
//                    Long row = null;
//                    BaseResponse response = new BaseResponse();
//                    try {
//                        row = cupboard().withDatabase(getWritableDatabase()).put(quest);
//                        response.setCode(200);
//                        Log.d(TAG, "putUserRegisterData: ----------"+row+quest);
//                    }catch (Exception e){
//                        response.setMessage(e.getMessage());
//                        emitter.onError(e);
//                        Log.d(TAG, "putUserRegisterData--Exception: -----------"+e.getMessage());
//                    }
//                    emitter.onNext(response);
//                    emitter.onComplete();
//                }
//            }
//        });
//
//
//    }
//    //添加用户信息
//    public Observable<BaseResponse> putUserRegister(TestBean quest) {
//
//        PersonalInfoData infoData = new PersonalInfoData();
//                infoData.setPassWord("121212");
//                infoData.setSex(1);
//                infoData.setName("131231");
//
//        return Observable.create(new ObservableOnSubscribe<BaseResponse>() {
//            @Override
//            public void subscribe(ObservableEmitter<BaseResponse> emitter) throws Exception {
//                if (!isClosed()){
//                    Long row = null;
//                    BaseResponse response = new BaseResponse();
//                    try {
//                        row = cupboard().withDatabase(getWritableDatabase()).put(quest);
//                        response.setCode(200);
//                        Log.d(TAG, "putUserRegisterData: ----------"+row+quest);
//                    }catch (Exception e){
//                        response.setMessage(e.getMessage());
//                        emitter.onError(e);
//                        Log.d(TAG, "putUserRegisterData--Exception: -----------"+e.getMessage());
//                    }
//                    emitter.onNext(response);
//                    emitter.onComplete();
//                }
//            }
//        });
//
//
//    }


}

