#include "cd.h"

int changeDirectory(char *s)//gaurenteed "cd " s[2] = " "
{
	char temp[MAX];
	strcpy(temp, &s[3]);	
	strip(temp);
	if(chdir(temp) != 0)
	{
		printf("mssh: cd: %s: No such file or directory\n", &s[3]);
		return -1;
	}
	return 0;
}//end changeDirectory
