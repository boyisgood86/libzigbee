/*
**  Author: Rick
**  Note: Add Serial Port Interface 
**  Date: 2013.09.20 
*/

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h> 
#include <errno.h>
#include <sys/poll.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/select.h>
#include <errno.h> 
#include "util.h"


/*
**  Did This Device Need O_DSYNC | O_RSYNC | O_SYNC Mode ? 
**  FIXME:if we read data from tty device we using O_NDELAY mode to open this device
 */

#define  OPENMODE (O_RDWR | O_NOCTTY /*TTY FIXME:| O_NONBLOCK */| O_NDELAY)  

enum SERIALCONFIG{
	NONE = 100 ,
	HARD ,
	SOFT ,
	OOD ,
	EVEN ,
} ;

//-----------------------------------------------------------------
int ngb_Open(char* device)
{
	int fd = -1 ;
	if ( -1 != fd ) return -1 ;
	if ( -1 == ( fd = open(device,OPENMODE ) ) ){
		return -1 ;
	}
	return fd;	
}

//-----------------------------------------------------------------
void ngb_Close(int fd )
{
	if ( fd < 0 ) return;
	if ( -1 == close(fd) ){
		return;
	}
	fd = -1;
	return;	

}
//-----------------------------------------------------------------
//-----------------------------------------------------------------
/*
int ngb_no_timeout_Read(int fd, void *buff, int size)
{
	Thread::lock();
	int result = -1;
	int max_fd = fd + 1;
	int ReadSize = -1;
	fd_set readset;
	struct timeval tv;
	if (fd < 0|| size <= 0){
		Thread::unlock();
		return -1;
	}
	tv.tv_sec = 0 ;
	tv.tv_usec = 500 ;
	FD_ZERO(&readset) ;
	FD_SET(fd,&readset) ;
	result = select(max_fd,&readset,NULL,NULL,&tv);
	if(result < 0) {
		Thread::unlock();
		return -1;
	}
	if(result == 0){
		Thread::unlock();
		return 0;
	}else {
		if(FD_ISSET(fd, &readset)){
			memset(buff, 0, size);
			if((ReadSize = read(fd,buff,size)) < 0){
				Thread::unlock();
				return -1;
			}
		}
	}
		Thread::unlock() ;
		return ReadSize ;
}
//-----------------------------------------------------------------
int ngb_Read_unlock(int fd,void* buffer ,int size)
{	
//	Thread::lock() ;
	int result = -1 ;
	int max_fd = fd + 1 ;
	int ReadSize = -1 ;
	fd_set readset ; 
	struct timeval tv ;
	if (fd < 0 || size <= 0 ){
		goto OUT; 
	}
//	ALOGI("debug msg from jason+ ----->%s\n",__FUNCTION__);
	tv.tv_sec = 1 ;
	tv.tv_usec = 500000 ;
	FD_ZERO(&readset) ;
	FD_SET(fd,&readset) ;
	if ((result = select(max_fd,&readset,NULL,NULL,&tv)) != -1){
		if(FD_ISSET(fd,&readset)){
			memset(buffer,0,size) ;
			if((ReadSize = read(fd,buffer,size)) < 0){
				goto OUT;
			}
		}
//		Thread::unlock() ;
		return ReadSize ;
	
	}else{
		goto OUT ;
	}

//	Thread::unlock() ;
	return ReadSize ;
OUT:
//	Thread::unlock() ;
	return -1 ;
}

*/
//-----------------------------------------------------------------
int ngb_Read(int fd,void* buffer ,int size)
{	
//	Thread::lock() ;
	int result = -1 ;
	int max_fd = fd + 1 ;
	int ReadSize = -1 ;
	fd_set readset ; 
	struct timeval tv ;
	if (fd < 0 || size <= 0 ){
		goto OUT; 
	}
	tv.tv_sec = 3 ;
	tv.tv_usec = 500000 ;
	FD_ZERO(&readset) ;
	FD_SET(fd,&readset) ;
	if ((result = select(max_fd,&readset,NULL,NULL,&tv)) != -1){
		if(FD_ISSET(fd,&readset)){
			memset(buffer,0,size) ;
			if((ReadSize = read(fd,buffer,size)) < 0){
				goto OUT;
			}
		}
//		Thread::unlock() ;
		return ReadSize ;
	
	}else{
		goto OUT ;
	}

//	Thread::unlock() ;
	return ReadSize ;
OUT:
//	Thread::unlock() ;
	return -1 ;
}

//-----------------------------------------------------------------
int  ngb_Write(int fd,void* buffer,int size)
{
	int i;
	int write_size = -1;
//	Thread::lock() ;
	if ( fd < 0 || NULL == buffer ){
		goto OUT ;
	}
	if( (write_size = write( fd,buffer,size )) == -1){
		goto OUT ;
	}
//	Thread::unlock() ;
	return write_size;
OUT:
//	Thread::unlock() ;
	return -1 ;

}


//-----------------------------------------------------------------
static  speed_t GetSpeed(int speed)
{
	
	switch(speed) {
		case 0: return B0;
		case 50: return B50;
		case 75: return B75;
		case 110: return B110;
		case 134: return B134;
		case 150: return B150;
		case 200: return B200;
		case 300: return B300;
		case 600: return B600;
		case 1200: return B1200;
		case 1800: return B1800;
		case 2400: return B2400;
		case 4800: return B4800;
		case 9600: return B9600;
		case 19200: return B19200;
		case 38400: return B38400;
		case 57600: return B57600;
		case 115200: return B115200;
		case 230400: return B230400;
		case 460800: return B460800;
		case 500000: return B500000;
		case 576000: return B576000;
		case 921600: return B921600;
		case 1000000: return B1000000;
		case 1152000: return B1152000;
		case 1500000: return B1500000;
		case 2000000: return B2000000;
		case 2500000: return B2500000;
		case 3000000: return B3000000;
		case 3500000: return B3500000;
		case 4000000: return B4000000;
		default: return -1;
	}

}
int  ngb_SetSpeed(int fd , int speed)
{	

	if ( fd < 0 )  return -1;
	//if( 0 == isatty(fd) ) return -1;
	speed_t nspeed = 0;
	struct  termios  term;
	if ( tcgetattr( fd, &term ) < 0 ){
		//LOGI("ngb_SetSpeed tcgetattr( fd, &term ) < 0\n") ;
		return -1;
	}

	nspeed = GetSpeed(speed);
	term.c_cflag |= CLOCAL ;
	term.c_cflag |= CREAD ;

	if ( -1 == cfsetispeed( &term, nspeed ) ){
		//LOGI( "ngb_SetSpeed -1 == cfsetispeed( &term, nspeed )\n");
		return  -1;
	}
	if ( -1 == cfsetospeed( &term, nspeed ) ) {
		//LOGI( "ngb_SetSpeed -1 == cfsetospeed( &term, nspeed )\n");
		return  -1;
	}
	if ( -1 == tcsetattr( fd, TCSANOW ,&term) ) {
		//LOGI( "ngb_SetSpeed -1 == tcsetattr( &term, nspeed )\n");
		return -1;
	}

	//LOGI("Set Speed success\n") ;
	return 0;
	
}

#if 0
static SERIALCONFIG GetConfigValue(int config )
{

	switch(config){
	case NONE:
		return NONE ;
	case HARD:
		return HARD ;
	case SOFT:
		return SOFT ;
	case OOD :
		return OOD ;
	case EVEN:
		return EVEN ;
	default :
		return NONE ;
	}
	return NONE ;
}
#endif
//-----------------------------------------------------------------
int  ngb_SetParity(int fd,int config )
{
	if ( fd < 0 ) return -1;
	struct termios  term ;
	if ( tcgetattr( fd, &term ) < 0 ) return -1;
//	SERIALCONFIG value = GetConfigValue(config) ;
	
	if ( OOD == config  ){
		term.c_cflag |= PARENB;
		term.c_cflag |= PARODD;
		term.c_iflag |= (INPCK | ISTRIP) ;
		if ( -1 == tcsetattr( fd, TCSANOW ,&term) ) return -1;
		return 0;
	} 

	if ( EVEN == config ){
		term.c_iflag |= (INPCK | ISTRIP);
		term.c_cflag |= PARENB;
		term.c_cflag &= ~PARODD ;
		if ( -1 == tcsetattr( fd, TCSANOW ,&term) ) return -1;
		return 0;
	} 
	
	if ( NONE == config ){
		term.c_cflag &= ~PARENB;
		term.c_iflag &= ~(INPCK | ISTRIP) ;
		if ( -1 == tcsetattr( fd, TCSANOW ,&term) ) return -1;
		return 0;
	} 
	
	return -1;

}


//-----------------------------------------------------------------
int  ngb_SetBits(int fd , int digit)
{
	if ( fd < 0 ) return -1;
	struct termios  term ;
	if ( tcgetattr( fd, &term ) < 0 ){
		//LOGI("ngb_SetBits tcgetattr( fd, &term ) < 0\n");
		return -1;
	}

	switch(digit){
	case 5:
		term.c_cflag &=	~CSIZE;
		term.c_cflag |= CS5;
		break;
	case 6:
		term.c_cflag &=	~CSIZE;
		term.c_cflag |= CS6;
		break;
	case 7:
		term.c_cflag &=	~CSIZE;
		term.c_cflag |= CS7;
		break;
	case 8:
		term.c_cflag &=	~CSIZE;
		term.c_cflag |= CS8;
		break;
	default:
		break;	
	}
	
	if ( -1 == tcsetattr( fd, TCSANOW ,&term) ){
		//LOGI("ngb_SetBits tcsetattr( fd, &term ) < 0\n");
		return -1;
	}

	//LOGI("Set ngb_SetBits Success\n") ;
	return 0;
}


//-----------------------------------------------------------------
int  ngb_SetStopBits(int fd ,int digit)
{
	if ( fd < 0) return -1;
	struct termios term ;
	if ( tcgetattr( fd, &term ) < 0 ){
		return -1;
	}
	switch(digit){
	case 1:
		term.c_cflag &= ~CSTOPB;	
		break;
	case 2:
		term.c_cflag |= CSTOPB;	
		break;
	default:
	
		term.c_cflag &= ~CSTOPB;	
	}
    term.c_iflag &= ~(IXON | IXOFF | IXANY);
    term.c_iflag &= ~(ONLCR | OCRNL) ;



	term.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG) ;
	term.c_oflag &= ~OPOST ;	
	term.c_cc[VTIME] = 1 ;
	term.c_cc[VMIN] = 1; 
	tcflush(fd,TCIFLUSH) ;

	if ( -1 == tcsetattr( fd, TCSANOW ,&term) ){
		return -1;
	}

	return 0;
			
}


//-----------------------------------------------------------------
int  ngb_SetFlowControl(int fd, int config)
{

	if ( fd < 0 )  return -1;
	struct termios  term ;
	if ( tcgetattr( fd, &term ) < 0 ) return -1;
	
//	SERIALCONFIG value = GetConfigValue(config) ;

	if ( NONE == config ){

		term.c_cflag &= ~CRTSCTS;
		if ( -1 == tcsetattr( fd, TCSANOW ,&term) ) return -1;
		return 0;
	}

	if ( HARD == config ){

		term.c_cflag |= CRTSCTS;
		if ( -1 == tcsetattr( fd, TCSANOW ,&term) ) return -1;
		return 0;
	}

	if ( SOFT == config ){

		term.c_cflag |= (IXON | IXOFF | IXANY);
		if ( -1 == tcsetattr( fd, TCSANOW ,&term) ) return -1;
		return 0;
	}

	return -1;

}


