

#ifndef __DEVICE_H__
#define __DEVICE_H__
class Device{
public:
	Device() ;
	~Device() ;
	int  open_tty_device(const char* tty_name) ;
	void close_tty_device(void) ;
//	void read_tty_device(void) ;
	int  set_speed_tty_device(int speed) ;
	int  set_parity_tty_device(int parity) ;
	int  set_bits_tty_device(int bits) ;
	int  set_stop_bits(int stopbits) ;
	int  set_flow_control_tty_device(int flow) ;
//    int  operator_device(const char* mac,char type ,char val/*if need set it else set 0*/)	;
	int  operator_device(const char* mac,char type,char cmd,char val) ;
	char * list_device(void);
	char * read_device(void);
	char * write_device(const char* mac, char type, char cmd, char val);
	char * list_device_new(const char* mac, char type, char cmd, char val);
	
private:
	int fd ;
};
#endif
