#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(void) {
	pid_t child_pid = fork();
	switch(child_pid) {
		case -1:
			// ERROR
			printf("ERROR: Can't create child process\n");
			break;
		case 0:
			// CHILD PROCESS
			printf("CHILD: PID == %d\n", getpid());
			exit(0);
		default:
			// PAR PROCESS
			printf("PARENT: PID == %d; CHILD PID == %d\n",
				getpid(), child_pid);
			break;
	}
	return 0;
}
