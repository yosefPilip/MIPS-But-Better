# MIPS But Better

**MIPS But Better** is an enhanced MIPS assembly language that extends standard MIPS with powerful, easy-to-use instructions. It makes MIPS programming more intuitive and reduces the complexity of common tasks.

## 🚀 Getting Started

### How to Use MIPS But Better

1. **Open MARS**
   ```bash
   java -jar Mars.jar
   ```

2. **Switch to MIPS But Better**
   - Go to **Tools → Language Switcher**
   - Select **"MIPS But Better"** from the dropdown
   - Click **OK**

3. **Start Coding!**
   - Write your assembly code using the enhanced instructions
   - Assemble and run as usual

### Building the Language

If you need to rebuild the language JAR:

```bash
# On Windows
java BuildCustomLang MIPSButBetter.java

# Or use the batch script
build_and_test.bat
```

## 📚 New Instructions

### Arithmetic Operations

#### `mul $rd, $rs, $rt`
**Multiply two registers**
- **Syntax**: `mul $t0, $t1, $t2`
- **Effect**: `$t0 = $t1 × $t2`
- **Example**:
  ```mips
  addi $t1, $zero, 5
  addi $t2, $zero, 3
  mul $t0, $t1, $t2    # $t0 = 15
  ```

#### `muli $rd, $rs, immediate`
**Multiply register by immediate value**
- **Syntax**: `muli $t1, $t2, 100`
- **Effect**: `$t1 = $t2 × 100`
- **Example**:
  ```mips
  addi $t0, $zero, 5
  muli $t1, $t0, 4     # $t1 = 20
  ```

#### `div $rd, $rs, $rt`
**Divide two registers**
- **Syntax**: `div $t0, $t1, $t2`
- **Effect**: `$t0 = $t1 ÷ $t2`
- **Example**:
  ```mips
  addi $t1, $zero, 15
  addi $t2, $zero, 3
  div $t0, $t1, $t2    # $t0 = 5
  ```

#### `divi $rd, $rs, immediate`
**Divide register by immediate value**
- **Syntax**: `divi $t1, $t2, 5`
- **Effect**: `$t1 = $t2 ÷ 5`
- **Example**:
  ```mips
  addi $t0, $zero, 20
  divi $t1, $t0, 4     # $t1 = 5
  ```

#### `subi $rd, $rs, immediate`
**Subtract immediate from register**
- **Syntax**: `subi $t1, $t2, 100`
- **Effect**: `$t1 = $t2 - 100`
- **Example**:
  ```mips
  addi $t0, $zero, 50
  subi $t1, $t0, 10    # $t1 = 40
  ```

#### `mod $rd, $rs, immediate`
**Modulo operation**
- **Syntax**: `mod $t1, $t2, 75`
- **Effect**: `$t1 = $t2 % 75`
- **Example**:
  ```mips
  addi $t0, $zero, 77
  mod $t1, $t0, 10     # $t1 = 7
  ```

#### `pow $rd, $rs, immediate`
**Power operation**
- **Syntax**: `pow $t0, $t1, 2`
- **Effect**: `$t0 = $t1²` (or `$t1` raised to the power of immediate)
- **Example**:
  ```mips
  addi $t1, $zero, 5
  pow $t0, $t1, 2      # $t0 = 25
  pow $t2, $t1, 3      # $t2 = 125
  ```

#### `factorial $rd, $rs`
**Calculate factorial**
- **Syntax**: `factorial $t0, $t1`
- **Effect**: `$t0 = $t1!`
- **Example**:
  ```mips
  addi $t1, $zero, 5
  factorial $t0, $t1   # $t0 = 120
  ```

### Comparison and Branching

#### `ifeq $rs, $rt, label`
**If equal - branch if NOT equal**
- **Syntax**: `ifeq $t0, $t2, skip`
- **Effect**: If `$t0 != $t2`, jump to `skip`
- **Example**:
  ```mips
  addi $t0, $zero, 5
  addi $t1, $zero, 5
  ifeq $t0, $t1, different  # Won't branch (they're equal)
  different:
  ```

#### `ifne $rs, $rt, label`
**If not equal - branch if equal**
- **Syntax**: `ifne $t0, $t2, skip`
- **Effect**: If `$t0 == $t2`, jump to `skip`

#### `ifgt $rs, $rt, label`
**If greater than - branch if less than or equal**
- **Syntax**: `ifgt $t0, $t2, skip`
- **Effect**: If `$t0 <= $t2`, jump to `skip`

#### `iflt $rs, $rt, label`
**If less than - branch if greater than or equal**
- **Syntax**: `iflt $t0, $t2, skip`
- **Effect**: If `$t0 >= $t2`, jump to `skip`

### Min/Max Operations

#### `max $rd, $rs, $rt`
**Maximum of two registers**
- **Syntax**: `max $t0, $t1, $t2`
- **Effect**: `$t0 = max($t1, $t2)`
- **Example**:
  ```mips
  addi $t1, $zero, 5
  addi $t2, $zero, 8
  max $t0, $t1, $t2    # $t0 = 8
  ```

#### `maxi $rd, $rs, immediate`
**Maximum of register and immediate**
- **Syntax**: `maxi $t1, $t2, 100`
- **Effect**: `$t1 = max($t2, 100)`

#### `min $rd, $rs, $rt`
**Minimum of two registers**
- **Syntax**: `min $t0, $t1, $t2`
- **Effect**: `$t0 = min($t1, $t2)`
- **Example**:
  ```mips
  addi $t1, $zero, 5
  addi $t2, $zero, 8
  min $t0, $t1, $t2    # $t0 = 5
  ```

#### `mini $rd, $rs, immediate`
**Minimum of register and immediate**
- **Syntax**: `mini $t1, $t2, 150`
- **Effect**: `$t1 = min($t2, 150)`

### Input/Output

#### `print "string"`
**Print string literal**
- **Syntax**: `print "Hello, World!"`
- **Effect**: Prints the string directly to console
- **Example**:
  ```mips
  print "Hello, World!\n"
  print "This is easy!"
  ```

#### `printnum $rs`
**Print register value as number**
- **Syntax**: `printnum $t0`
- **Effect**: Prints the integer value in `$t0`
- **Example**:
  ```mips
  addi $t0, $zero, 42
  printnum $t0          # Prints: 42
  ```

#### `input $rd`
**Read string from user**
- **Syntax**: `input $t0`
- **Effect**: Reads user input and stores the string address in `$t0`
- **Example**:
  ```mips
  input $t0             # User types something
  # $t0 now contains address of the input string
  ```

### String Operations

#### `lstri $rd, "string"`
**Load string immediate**
- **Syntax**: `lstri $t2, "Hello World!"`
- **Effect**: Stores string in memory and loads its address into `$rd`
- **Example**:
  ```mips
  lstri $t0, "Hello"
  # $t0 now contains address to "Hello" in memory
  ```

#### `strlen $rd, $rs`
**Get string length**
- **Syntax**: `strlen $t1, $t0`
- **Effect**: `$t1 = length of string at address in $t0`
- **Example**:
  ```mips
  lstri $t0, "Hello"
  strlen $t1, $t0       # $t1 = 5
  ```

### Utility Operations

#### `rand $rd, $rs`
**Generate random number**
- **Syntax**: `rand $t1, $t0`
- **Effect**: `$t1 = random number between 0 and $t0` (inclusive)
- **Example**:
  ```mips
  addi $t0, $zero, 100
  rand $t1, $t0         # $t1 = random number 0-100
  ```

#### `avg $rd, $rs, $rt`
**Calculate average**
- **Syntax**: `avg $t2, $t0, $t1`
- **Effect**: `$t2 = ($t0 + $t1) / 2`
- **Example**:
  ```mips
  addi $t0, $zero, 10
  addi $t1, $zero, 20
  avg $t2, $t0, $t1     # $t2 = 15
  ```

#### `gcd $rd, $rs, $rt`
**Greatest Common Divisor**
- **Syntax**: `gcd $t2, $t0, $t1`
- **Effect**: `$t2 = GCD($t0, $t1)`
- **Example**:
  ```mips
  addi $t0, $zero, 48
  addi $t1, $zero, 18
  gcd $t2, $t0, $t1     # $t2 = 6
  ```

#### `pali $rs, label`
**Palindrome check**
- **Syntax**: `pali $t0, not_palindrome`
- **Effect**: Branches to label if string at `$t0` is NOT a palindrome
- **Example**:
  ```mips
  lstri $t0, "racecar"
  pali $t0, not_pal     # Won't branch (it's a palindrome)
  print "Is palindrome!"
  j end
  not_pal:
  print "Not a palindrome"
  end:
  ```

#### `j label`
**Unconditional jump**
- **Syntax**: `j target`
- **Effect**: Jump to label

## 📝 Example Programs

### Example 1: Simple Calculator
```mips
.text
main:
    addi $t0, $zero, 15
    addi $t1, $zero, 3
    
    add $t2, $t0, $t1
    printnum $t2        # Prints: 18
    
    sub $t2, $t0, $t1
    printnum $t2        # Prints: 12
    
    mul $t2, $t0, $t1
    printnum $t2        # Prints: 45
    
    div $t2, $t0, $t1
    printnum $t2        # Prints: 5
    
    li $v0, 10
    syscall
```

### Example 2: Finding Maximum
```mips
.text
main:
    addi $t0, $zero, 5
    addi $t1, $zero, 8
    addi $t2, $zero, 3
    
    max $t3, $t0, $t1  # $t3 = 8
    max $t3, $t3, $t2  # $t3 = 8
    
    print "Maximum is: "
    printnum $t3
    
    li $v0, 10
    syscall
```

### Example 3: String Operations
```mips
.text
main:
    lstri $t0, "Hello, MIPS But Better!"
    print "String address loaded\n"
    
    strlen $t1, $t0
    print "Length: "
    printnum $t1
    print "\n"
    
    lstri $t2, "racecar"
    pali $t2, not_pal
    print "racecar is a palindrome!\n"
    j end
    
    not_pal:
    print "Not a palindrome\n"
    
    end:
    li $v0, 10
    syscall
```

### Example 4: Mathematical Functions
```mips
.text
main:
    addi $t0, $zero, 5
    pow $t1, $t0, 2
    print "5^2 = "
    printnum $t1
    print "\n"
    
    addi $t0, $zero, 5
    factorial $t1, $t0
    print "5! = "
    printnum $t1
    print "\n"
    
    addi $t0, $zero, 48
    addi $t1, $zero, 18
    gcd $t2, $t0, $t1
    print "GCD(48, 18) = "
    printnum $t2
    print "\n"
    
    li $v0, 10
    syscall
```

## 🔍 Complete Instruction Reference

| Instruction | Format | Description |
|------------|--------|-------------|
| `add $rd, $rs, $rt` | R | Add with overflow check |
| `addi $rd, $rs, imm` | I | Add immediate with overflow check |
| `sub $rd, $rs, $rt` | R | Subtract with overflow check |
| `subi $rd, $rs, imm` | I | Subtract immediate |
| `mul $rd, $rs, $rt` | R | Multiply |
| `muli $rd, $rs, imm` | I | Multiply immediate |
| `div $rd, $rs, $rt` | R | Divide |
| `divi $rd, $rs, imm` | I | Divide immediate |
| `mod $rd, $rs, imm` | I | Modulo |
| `pow $rd, $rs, imm` | I | Power operation |
| `factorial $rd, $rs` | R | Factorial |
| `ifeq $rs, $rt, label` | I-Branch | Branch if not equal |
| `ifne $rs, $rt, label` | I-Branch | Branch if equal |
| `ifgt $rs, $rt, label` | I-Branch | Branch if less than or equal |
| `iflt $rs, $rt, label` | I-Branch | Branch if greater than or equal |
| `max $rd, $rs, $rt` | R | Maximum |
| `maxi $rd, $rs, imm` | I | Maximum with immediate |
| `min $rd, $rs, $rt` | R | Minimum |
| `mini $rd, $rs, imm` | I | Minimum with immediate |
| `print "string"` | R | Print string literal |
| `printnum $rs` | R | Print number |
| `input $rd` | I | Read input string |
| `lstri $rd, "string"` | R | Load string immediate |
| `strlen $rd, $rs` | R | String length |
| `rand $rd, $rs` | R | Random number |
| `avg $rd, $rs, $rt` | R | Average |
| `gcd $rd, $rs, $rt` | R | Greatest common divisor |
| `pali $rs, label` | I-Branch | Palindrome check |
| `j label` | J | Unconditional jump |

## ⚠️ Notes

- All arithmetic operations include **overflow detection**
- Division operations check for **division by zero**
- String operations automatically handle **null terminators**
- The language is **backward compatible** with standard MIPS instructions
- All standard MIPS instructions still work as expected

## 🎯 Why MIPS But Better?

MIPS But Better simplifies assembly programming by reducing complex multi-instruction operations into single, intuitive instructions. Operations that typically require 3-5 instructions in standard MIPS (like multiplication, printing, comparisons, and string handling) are now accomplished with one instruction. This dramatically reduces code size, improves readability, and makes common tasks like I/O, mathematical functions, and string manipulation much more accessible. The language includes built-in safety features like overflow detection and division-by-zero checking, while maintaining full backward compatibility with standard MIPS. The result is code that reads more like high-level programming while still maintaining the educational value of assembly language.

---

**Happy Coding with MIPS But Better!** 🚀
