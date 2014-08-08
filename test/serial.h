/*
**  Author: Rick 
**  Note: Serial Port Interface
**  Date: 2013.09.20
*/

#ifndef __SERIAL_H__
#define __SERIAL_H__

extern int ngb_Open(char*);
extern void ngb_Close(int);
extern int  ngb_Read(int,void* buffer ,int size);
extern int  ngb_Write(int,void* buffer,int size);
extern int  ngb_SetSpeed(int,int speed);
extern int  ngb_SetParity(int,int );
extern int  ngb_SetBits(int,int digit);
extern int  ngb_SetStopBits(int,int digit);
extern int  ngb_SetFlowControl(int,int);


#endif
