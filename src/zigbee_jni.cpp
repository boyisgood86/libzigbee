
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <utils/Log.h>
#include "device.h"


static Device* device ;
//---------------------------------------------------------------
static jstring 
stoJstring(JNIEnv* env, const char* pat)
{
     jclass strClass = env->FindClass("java/lang/String");
     jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
     jbyteArray bytes = env->NewByteArray(strlen(pat));
     env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
     //jstring encoding = env->NewStringUTF("GB2312");
     jstring encoding = env->NewStringUTF("UTF-8");
     return (jstring)env->NewObject(strClass, ctorID, bytes, encoding);
}

static jint
Open_tty_device(JNIEnv *env ,jobject obj ,jstring tty)
{
	const char* tty_name = env->GetStringUTFChars(tty,0) ;
	return device->open_tty_device(tty_name) ;
}

static void
Close_tty_device(JNIEnv *env ,jobject obj )
{
	device->close_tty_device() ;
}

static jint
Set_speed_tty_device(JNIEnv* env,jobject obj,jint speed)
{
	return device->set_speed_tty_device(speed) ;
}
static jint
Set_parity_tty_device(JNIEnv *env,jobject obj,jint parity)
{
	return device->set_parity_tty_device(parity) ;
}
static jint
Set_bits_tty_device(JNIEnv* env,jobject obj ,jint bits)
{	
	return device->set_bits_tty_device(bits) ;
}

static jint
Set_stop_bits(JNIEnv *env,jobject obj,jint stopbits)
{
	return device->set_stop_bits(stopbits) ;
}

static jint
Set_flow_control_tty_device(JNIEnv* env,jobject obj,jint flow)
{
	return device->set_flow_control_tty_device(flow) ;
}


//---------------------------------------------------------------
/*
#if 0
static jint
Operator_device(JNIEnv *env ,jobject obj,jstring value,jint type ,jint val)
{
	const char* mac = env ->GetStringUTFChars(value,0) ;
	return device->operator_device(mac,type,val) ;
}
#endif
*/
static jint
Operator_device_Cmd(JNIEnv *env ,jobject obj,jstring value,jint type ,jint cmd, jint val)
{
	const char* mac = env ->GetStringUTFChars(value,0) ;
//	ALOGI("from jason debug msg ------>  Now in OperatorCmd function !\n");
	return device->operator_device(mac,type,cmd, val) ;
}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

static jstring
List_device(JNIEnv *env ,jobject obj)
{
//	JNIEnv *env;
	jstring result ;
//	ALOGI("from jason debug msg------------------>Now here in List_device!\n");
	result = stoJstring(env, (device->list_device() ) ); 
	return result;
}

/*******************************************************************/
static jstring
Read_device(JNIEnv *env ,jobject obj)
{
//	JNIEnv *env;
	jstring result ;
//	ALOGI("from jason debug msg------------------>Now here in List_device!\n");
	result = stoJstring(env, (device->read_device() ) ); 
	return result;
}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++

static jstring
List_device_new(JNIEnv *env ,jobject obj, jstring value, jint type, jint cmd, jint val)
{
//	JNIEnv *env;
	jstring result ;
	const char* mac = env ->GetStringUTFChars(value,0) ;
//	ALOGI("from jason debug msg------------------>Now here in List_device!\n");
	result = stoJstring(env, (device->list_device_new(mac, type, cmd, val) ) ); 
	return result;
}

/********************************************************/

static jstring
Write_device(JNIEnv *env ,jobject obj, jstring value, jint type, jint cmd, jint val)
{
//	JNIEnv *env;
	jstring result ;
	const char* mac = env ->GetStringUTFChars(value,0) ;
//	ALOGI("from jason debug msg------------------>Now here in List_device!\n");
	result = stoJstring(env, (device->write_device(mac, type, cmd, val) ) ); 
	return result;
}
#if 0
static jstring
Operator_device_Cmd_String(JNIEnv *env ,jobject obj,jstring value,jint type ,jint cmd, jint val)
{
	const char * mac = env -> GetStringUTFChars(value, 0);
	jstring result ;
	result = stoJstring(env, (device->operator_device_string(mac, type, cmd, val) ) ); 
	return result;
}

#endif
//--------------------------------------------------------------C
static JNINativeMethod  gMethods[] = {
    {"OperatorCmd","(Ljava/lang/String;III)I",(void *)Operator_device_Cmd},
	{"OpenTty","(Ljava/lang/String;)I",(void*)Open_tty_device},
	{"CloseTty","()V",(void*)Close_tty_device},
	{"SetTtySpeed","(I)I",(void*)Set_speed_tty_device},
	{"SetTtyParity","(I)I",(void*)Set_parity_tty_device},
	{"SetTtyBits","(I)I",(void*)Set_bits_tty_device},
	{"SetTtyStopBits","(I)I",(void*)Set_stop_bits},
	{"SetTtyFlowControl","(I)I",(void*)Set_flow_control_tty_device},
    {"ListDevice","()Ljava/lang/String;",(void*)List_device},
    {"ReadDevice","()Ljava/lang/String;",(void*)Read_device},
    {"ListDevice_new","(Ljava/lang/String;III)Ljava/lang/String;",(void*)List_device_new},
    {"WriteDevice","(Ljava/lang/String;III)Ljava/lang/String;",(void*)Write_device},
//    {"OperatorCmdString","(Ljava/lang/String;III)Ljava/lang/String;",(void*)Operator_device_Cmd_String},
};

//---------------------------------------------------------------
static int registerNativeMethods(JNIEnv* env, const char* className,
    JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;


    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}
//------------------------------------------------------------------------------
 
static int register_tuner_jni(JNIEnv *env){
  return registerNativeMethods(env,"cn/acadiatech/telecom/box/engine/DataProvider", gMethods,  sizeof(gMethods) / sizeof(gMethods[0]));
}

//------------------------------------------------------------------------------

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
    jint result = -1;
	device = new Device() ;
    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        return result;
    }
    assert(env != NULL);

    if (register_tuner_jni(env) < 0)
    {
        return result;
    }

    result = JNI_VERSION_1_4;

    return result;
}

//---------------------------------------------------------------

