.class public large-program-fac
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/&lt;init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
invokestatic large-program-fac/main()I
pop
return
.end method
.method public static main()I
.limit locals 100
.limit stack 100
pop
pop
pop
pop
ldc 0
ireturn
return
.end method
.method public static fac(I)I
.limit locals 100
.limit stack 100
ldc 1
istore 1
iload 1
pop
iload 0
istore 2
iload 2
pop
WHILE:
ldc 1
iload 2
ldc 0
if_icmpgt L
pop
ldc 0
L:
ifeq END
iload 1
iload 2
imul
istore 1
iload 1
pop
iload 2
ldc 1
isub
istore 2
iload 2
pop
goto WHILE
END
iload 1
ireturn
return
.end method
.method public static rfac(I)I
.limit locals 100
.limit stack 100
ldc 1
iload 3
ldc 0
if_icmpeq L
pop
ldc 0
L:
ifeq END
ldc 1
istore 4
iload 4
pop
goto TRUE
FALSE:
iload 3
imul
istore 4
iload 4
pop
TRUE:
iload 4
ireturn
return
.end method
.method public static mfac(I)I
.limit locals 100
.limit stack 100
ldc 1
iload 5
ldc 0
if_icmpeq L
pop
ldc 0
L:
ifeq END
ldc 1
istore 6
iload 6
pop
goto TRUE
FALSE:
iload 5
imul
istore 6
iload 6
pop
TRUE:
iload 6
ireturn
return
.end method
.method public static nfac(I)I
.limit locals 100
.limit stack 100
ldc 1
iload 7
ldc 0
if_icmpne L
pop
ldc 0
L:
ifeq END
iload 7
imul
istore 8
iload 8
pop
goto TRUE
FALSE:
ldc 1
istore 8
iload 8
pop
TRUE:
iload 8
ireturn
return
.end method
.method public static ifac(I)I
.limit locals 100
.limit stack 100
ireturn
return
.end method
.method public static ifac2f(II)I
.limit locals 100
.limit stack 100
ldc 1
iload 10
iload 11
if_icmpeq L
pop
ldc 0
L:
ifeq END
iload 10
istore 12
iload 12
pop
goto TRUE
FALSE:
ldc 1
iload 10
iload 11
if_icmpgt L
pop
ldc 0
L:
ifeq END
ldc 1
istore 12
iload 12
pop
goto TRUE
FALSE:
iload 10
iload 11
iadd
ldc 2
idiv
istore 13
iload 13
pop
imul
istore 12
iload 12
pop
TRUE:
TRUE:
iload 12
ireturn
return
.end method
