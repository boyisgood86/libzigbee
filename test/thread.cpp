
#include "thread.h"

static pthread_mutex_t mutex ;

//-------------------------------------------------------------------
Thread::Thread(){}
//-------------------------------------------------------------------
int Thread::lock()
{
	int flags;
	if((flags=pthread_mutex_lock(&mutex))==0)
		return -1;
	else
		return 0;
}

//-------------------------------------------------------------------

int Thread::unlock()
{
	int flags;
	if((flags=pthread_mutex_unlock(&mutex))==0)
		return -1;
	else
		return 0;
}

//-------------------------------------------------------------------

int Thread::join(pthread_t tid,void **ptr)
{
	if(pthread_join(tid,ptr)==0)
		return 0;
	else
		return -1;
}

//-------------------------------------------------------------------
int Thread::detach(pthread_t tid)
{
	if(pthread_detach(tid)==0)
		return 0;
	else
		return -1;
}

//-------------------------------------------------------------------
Thread::~Thread()
{

}


