.class public boolean
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic boolean/main()I
pop
return
.end method
.method public static ff()Z
.limit locals 100
.limit stack 100
pop
ldc 0
ireturn
return
.end method
.method public static tt()Z
.limit locals 100
.limit stack 100
pop
ldc 1
ireturn
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
ldc 1
istore_0
ldc 0
istore_1
iload 1
ifeq Lf
ifeq Lf
ldc 1
goto LEnd
Lf:
ldc 0
LEnd:
istore_2
iload 0
ifne LTrue
ifne LTrue
ldc 0
goto LEnd
LTrue:
ldc 1
LEnd:
istore_3
iload 0
ifeq Lf
ifeq Lf
ldc 1
goto LEnd
Lf:
ldc 0
LEnd:
istore_4
iload 1
ifne LTrue
ifne LTrue
ldc 0
goto LEnd
LTrue:
ldc 1
LEnd:
istore_5
ldc 0
ireturn
return
.end method
