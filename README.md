AndroidSensorFusion  
com.goatstone.util.SensorFusion
===================

Get, fuse and display sensor information for Android.

Usage:

    private SensorFusion sensorFusion;
    sensorFusion = new SensorFusion();
    sensorFusion.setMode(SensorFusion.Mode.ACC_MAG);

    double azimuthValue = sensorFusion.getAzimuth();
    double rollValue =  sensorFusion.getRoll();
    double pitchValue =  sensorFusion.getPitch();
