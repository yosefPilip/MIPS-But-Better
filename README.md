# MIPS But Better

## Instructions Implemented

**Arithmetic**: `add`, `addi`, `sub`, `subi`, `mul`, `muli`, `div`, `divi`, `mod`, `pow`, `factorial`

**Comparison/Branching**: `ifeq`, `ifne`, `ifgt`, `iflt`, `j`

**Min/Max**: `max`, `maxi`, `min`, `mini`

**I/O**: `print "string"`, `printnum $rs`, `input $rd`

**String Operations**: `lstri $rd, "string"`, `strlen $rd, $rs`

**Utilities**: `rand`, `avg`, `gcd`, `pali`

## How to Run/Test in MARS LE

1. Build the language JAR: `java BuildCustomLang MIPSButBetter.java` (or use `build_and_test.bat`)
2. Open MARS: `java -jar Mars.jar`
3. Go to **Tools → Language Switcher** and select **"MIPS But Better"**
4. Write assembly code using the new instructions and assemble/run as usual

The JAR file (`MIPSButBetter.jar`) is located in `mars/mips/instructions/customlangs/` and will automatically appear in the Language Switcher.

## Examples

### Example 1: Input and Palindrome Check
```mips
.text
main:
    input $t0
    strlen $t1, $t0
    print "Length of input String: "
    printnum $t1
    print "\n"
    pali $t0, isNotPali
    print "Input is a palindrome."
    j end
isNotPali:
    print "Input is not a palindrome."
end:
    li $v0, 10
    syscall
```

### Example 2: FizzBuzz
```mips
.text
main:
    addi $t0, $zero, 1
    addi $t1, $zero, 101
    
loop:
    iflt $t0, $t1, end
    
continue_loop:
    mod $t3, $t0, 15
    ifeq $t3, $zero, check_fizz
    print "FizzBuzz\n"
    j increment
    
check_fizz:
    mod $t3, $t0, 3
    ifeq $t3, $zero, check_buzz
    print "Fizz\n"
    j increment
    
check_buzz:
    mod $t3, $t0, 5
    ifeq $t3, $zero, print_number
    print "Buzz\n"
    j increment
    
print_number:
    printnum $t0
    print "\n"
    
increment:
    addi $t0, $t0, 1
    j loop

end:
    li $v0, 10
    syscall
```

## Why MIPS But Better?

MIPS But Better simplifies assembly programming by reducing complex multi-instruction operations into single, intuitive instructions. Operations that typically require 3-5 instructions in standard MIPS (like multiplication, printing, comparisons, and string handling) are now accomplished with one instruction. This dramatically reduces code size, improves readability, and makes common tasks like I/O, mathematical functions, and string manipulation much more accessible. The language includes built-in safety features like overflow detection and division-by-zero checking, while maintaining full backward compatibility with standard MIPS. The result is code that reads more like high-level programming while still maintaining the educational value of assembly language.
