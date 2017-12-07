.class public scopes-reuse-name
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic scopes-reuse-name/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 0
istore_0
ldc 1
ifeq END
ldc 1
istore_1
goto TRUE
FALSE:
TRUE:
pop
ldc 1
ifeq END
ldc 2
istore 2
iload 2
pop
goto TRUE
FALSE:
TRUE:
pop
ldc 0
ifeq END
goto TRUE
FALSE:
ldc 3
istore_3
TRUE:
pop
ldc 0
ifeq END
goto TRUE
FALSE:
ldc 4
istore 4
iload 4
pop
TRUE:
pop
ldc 0
istore_5
WHILE:
ldc 1
iload 5
dup
ldc 1
iadd
istore 5
ldc 1
if_icmplt L
pop
ldc 0
L:
ifeq END
ldc 5
istore_6
goto WHILE
END
pop
ldc 0
istore_7
WHILE:
ldc 1
iload 7
dup
ldc 1
iadd
istore 7
ldc 1
if_icmplt L
pop
ldc 0
L:
ifeq END
ldc 6
istore 8
iload 8
pop
goto WHILE
END
pop
ldc 7
istore_9
pop
ldc 8
istore 10
iload 10
pop
pop
ldc 0
ireturn
return
.end method
