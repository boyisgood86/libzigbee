
#ifndef __THREAD_H__
#define __THREAD_H__
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <sys/types.h>
#include <errno.h>
#include <malloc.h>


class Thread{
public:
	Thread();
	static int lock();
	static int unlock();
	static int join(pthread_t tid,void **ptr);
	static int detach(pthread_t tid);
	~Thread();
};


#endif
