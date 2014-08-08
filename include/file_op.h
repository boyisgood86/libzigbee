#ifndef __FILEOP_H__
#define __FILEOP_H__

#include <utils/Log.h>

//#define FILE_DEBUG

//extern char file_buff[256]={0};
#define FILE_NAME  "/mnt/sdcard/jason.txt"
#ifndef FILE_DEBUG
#define DEBUG(fm, args...) do{ }while(0)
#else
#define DEBUG(fmt, args...) snprintf(file_buff,sizeof(file_buff),fmt, ##args); \
		      write2file(FILE_NAME,file_buff,sizeof(file_buff));
#endif


int write2file(const char*filename,char *data,int len);


#endif
