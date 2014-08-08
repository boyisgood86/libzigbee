#include <stdio.h>
#include <string.h>

char buf[16]={0x12,0x80,0x12,0x22,0x00,0x13,2,3,'d','e','w',0x00,0x55,'#',5,'d'};
int main()
{
	char res[256];
	int i;
	for(i=0;i<16;i++)
	{
		sprintf((res+(i*3)),"%2X,",buf[i]);
	}
	printf("res=%s\n",res);
}
