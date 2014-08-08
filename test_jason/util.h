#ifndef __UTIL_H_
#define __UTIL_H_

int		operator_device(int fd, int op);
int		ngb_Open(char* device);
void	ngb_Close(int fd );
int		ngb_Read(int fd,void* buffer ,int size);
int		ngb_Write(int fd,void* buffer,int size);
int		ngb_SetSpeed(int fd , int speed);
int		ngb_SetParity(int fd,int config );
int		ngb_SetBits(int fd , int digit);
int		ngb_SetStopBits(int fd ,int digit);
int		ngb_SetFlowControl(int fd, int config);



#endif /*__UTIL_H_*/

