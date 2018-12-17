package com.example.jgarcia.callapp.interfaces;

public interface callMethods {

    public void initiateCall();

    public boolean checkIfAlreadyHaveCallPermission();

    public void requestCallPermission();

    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults);

    public void makeTheCall(String numberString);

}
