#include "pipes.h"

int containsPipe(char *s)
{
	int x, pipes = 0;
	for(x = 0; x < strlen(s) -1; x++)
	{
		if(s[x] == '|')
			pipes++;
	}
	return pipes;
}

char ** parsePrePipe(char *s, int * preCount)
{
	int x, place = 0, tokens;
	char **argv = NULL;
	for(x = 0; x < strlen(s) -1; x++)
	{
		if(s[x] == '|')
			place = x;	
	}//place is location of pipe
	char *c = (char *)calloc(place+1, sizeof(char));//" |"
	strncpy(c, s, place-1);
	makeargs(c, &argv, &tokens);
	*preCount = tokens;
	free(c);
	return argv;
}

char ** parsePostPipe(char *s, int * postCount)
{
	int x, place = 0, tokens;
	char **argv = NULL;
	for(x = 0; x < strlen(s) -1; x++)
	{
		if(s[x] == '|')
			place = x;
	}
	char *c = (char *)calloc(strlen(s) - place, sizeof(char));//...
	strcpy(c, &s[place+1]);
	makeargs(c, &argv, &tokens);
	*postCount = tokens;
	free(c);
	return argv;
}

void pipeIt(char ** prePipe, char ** postPipe)
{
	pid_t pid, w;
	int fd[2], res, status;

	res = pipe(fd);

	if(res < 0)
	{
		printf("Pipe Failure\n");
		exit(-1);
	}// end if

	pid = fork();
	if(pid < 0)
	{
		printf("Error forking child.\n");
		exit(-99);
	}//error
	
	else if(pid != 0)
	{
		waitpid(pid, &status, 0);
	}// end if AKA parent
	
	else
	{
		res = pipe(fd);
		
		if(res < 0)
		{
			printf("Second Pipe Failed\n");
			exit(pid);
		}
		
		pid = fork();
		if(pid < 0)
		{
			printf("Error forking grandchild.\n");
			exit(-99);
		}//error	
		
		else if(pid != 0)
		{
			close(fd[1]);
			close(0);
			dup(fd[0]);
			close(fd[0]);
			res = execvp(postPipe[0], postPipe);
			
				if(res == -1)
					exit(pid);
		}//child/parent
		
		else
		{
			close(fd[0]);
			close(1);
			dup(fd[1]);
			close(fd[1]);
			res = execvp(prePipe[0], prePipe);
			
				if(res == -1)
					exit(pid);
		}//grandchild/child
	}// end else AKA child
}








