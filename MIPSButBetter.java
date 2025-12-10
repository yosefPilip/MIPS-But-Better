package mars.mips.instructions.customlangs;
import mars.simulator.*;
import mars.mips.hardware.*;
import mars.mips.instructions.syscalls.*;
import mars.*;
import mars.util.*;
import mars.mips.instructions.*;
import mars.util.SystemIO;
import java.util.Random;


public class MIPSButBetter extends CustomAssembly {
    // Static counter to track next available address for string storage
    private static int nextStringAddress = 0x10010000;
    
    @Override
    public String getName() {
        return "MIPS But Better ";
    }

    @Override
    public String getDescription() {
        return "An improved version of MIPS with better functionality and easier use";
    }

    @Override
    protected void populate(){

        //Simple instructions
        // ============= ADD INSTRUCTIONS =============
        instructionList.add(
                new BasicInstruction("add $t0, $t1,$t2",
                        "Add: set $t0 to ($t1 plus $t2)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 100000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = RegisterFile.getValue(operands[2]);
                                int sum = add1 + add2;

                                if ((add1 >= 0 && add2 >= 0 && sum < 0) || (add1 < 0 && add2 < 0 && sum >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], sum);
                            }

                        }));

        instructionList.add(
                new BasicInstruction("addi $t1,$t2,-100",
                        "Addition immediate with overflow : set $t1 to ($t2 plus signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "100000 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = operands[2] << 16 >> 16;
                                int sum = add1 + add2;
                                // overflow on A+B detected when A and B have same sign and A+B has other sign.
                                if ((add1 >= 0 && add2 >= 0 && sum < 0)
                                        || (add1 < 0 && add2 < 0 && sum >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));
        // ============= SUBTRACT INSTRUCTIONS =============
        instructionList.add(
                new BasicInstruction("sub $t0, $t1,$t2",
                        "Sub: set $t0 to ($t1 minus $t2)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 100010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int register = RegisterFile.getValue(operands[1]);
                                int subtractor = RegisterFile.getValue(operands[2]);
                                int diffirence = register - subtractor;

                                if ((register >= 0 && subtractor < 0 && diffirence < 0) || (register <= 0 && subtractor > 0 && diffirence >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], diffirence);
                            }

                        }));

        instructionList.add(
                new BasicInstruction("subi $t1,$t2,100",
                        "subtract immediate with overflow : set $t1 to ($t2 plus signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "100010 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[1]);
                                int subtractor = operands[2] << 16 >> 16;
                                int diffirence = reg1 - subtractor;
                                // overflow on A+B detected when A and B have same sign and A+B has other sign.
                                if ((reg1 >= 0 && subtractor < 0 && diffirence < 0)
                                        || (reg1 < 0 && subtractor >= 0 && diffirence > 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], diffirence);
                            }
                        }));
        // ============= MULTIPLY INSTRUCTIONS =============
        instructionList.add(
                new BasicInstruction("mul $t0,$t1,$t2",
                        "Multiply: set $t0 to ($t1 times $t2)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 011000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int multiplicand = RegisterFile.getValue(operands[1]);
                                int multiplier = RegisterFile.getValue(operands[2]);

                                // Multiply using long to detect overflow
                                long product = (long) multiplicand * (long) multiplier;

                                // Check if product fits in 32-bit signed integer
                                if (product > Integer.MAX_VALUE || product < Integer.MIN_VALUE)
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }

                                RegisterFile.updateRegister(operands[0], (int) product);
                            }
                        }));

         instructionList.add(
                new BasicInstruction("muli $t1,$t2,100",
                        "multiply immediate with overflow : set $t1 to ($t2 times signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "011000 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int multiplicand = RegisterFile.getValue(operands[1]);
                                int multiplier = operands[2] << 16 >> 16;
                                long product =(long) multiplicand * (long) multiplier;
                                // overflow on A+B detected when A and B have same sign and A+B has other sign.
                                // if ((add1 >= 0 && add2 >= 0 && sum < 0)
                                //         || (add1 < 0 && add2 < 0 && sum >= 0))
                                // {
                                //     throw new ProcessingException(statement,
                                //             "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                // }
                                RegisterFile.updateRegister(operands[0],(int) product);
                            }
                        }));

// ============= DIVIDE INSTRUCTIONS =============
        instructionList.add(
                new BasicInstruction("div $t0,$t1,$t2",
                        "Divide: set $t0 to ($t1 divided by $t2)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 011010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int dividend = RegisterFile.getValue(operands[1]);
                                int divisor = RegisterFile.getValue(operands[2]);

                                // Check for division by zero
                                if (divisor == 0)
                                {
                                    throw new ProcessingException(statement,
                                            "divide by zero", Exceptions.DIVIDE_BY_ZERO_EXCEPTION);
                                }

                                // Check for overflow case: most negative number / -1
                                if (dividend == Integer.MIN_VALUE && divisor == -1)
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }

                                int quotient = dividend / divisor;
                                RegisterFile.updateRegister(operands[0], quotient);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("divi $t1,$t2,100",
                        "divide immediate with overflow : set $t1 to ($t2 divided by signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "011010 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[1]);
                                int divisor = operands[2] << 16 >> 16;
                                long quotient = (long)reg1 / (long)divisor;
                                if (quotient > Integer.MAX_VALUE || quotient < Integer.MIN_VALUE)
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0],(int) quotient);
                            }
                        }));
        // ============= LOGIC INSTRUCTIONS =============
        instructionList.add(
                new BasicInstruction("ifeq $t0, $t2, Jump",
                        "If equal: If $t0 is equal to $t2 continue, if not then jump to adress",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000100 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[0]);
                                int reg2 = RegisterFile.getValue(operands[1]);

                                if(reg1 != reg2)
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("ifne $t0, $t2, Jump",
                        "If not equal: If $t0 does not equal to $t2 continue, if not then jump to adress",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000101 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[0]);
                                int reg2 = RegisterFile.getValue(operands[1]);

                                if(reg1 == reg2)
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("ifgt $t0, $t2, Jump",
                        "If greater than: If $t0 is greater than $t2 continue, if not then jump to adress",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000110 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[0]);
                                int reg2 = RegisterFile.getValue(operands[1]);

                                if(reg1 <= reg2)
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));
        instructionList.add(
                new BasicInstruction("iflt $t0, $t2, Jump",
                        "If less than: If $t0 is less than $t2 continue, if not then jump to address",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000111 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[0]);
                                int reg2 = RegisterFile.getValue(operands[1]);

                                if(reg1 >= reg2)
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));

        // ============= MAXIMUM INSTRUCTIONS =============

        instructionList.add(
                new BasicInstruction("max $t0, $t1,$t2",
                        "Maximum: sets $t0 to the larger value between $t1 and $t2",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 101011",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[1]);
                                int reg2 = RegisterFile.getValue(operands[2]);

                                int max = Math.max(reg1,reg2);
                                RegisterFile.updateRegister(operands[0], max);

                            }
                        }));

        instructionList.add(
                new BasicInstruction("maxi $t1,$t2,100",
                        "maximum immediate with overflow : set $t1 to the larger value between $t2 and a 16 bit immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "101011 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[1]);
                                int immediate = operands[2] << 16 >> 16;
                                int max = Math.max(reg1,immediate);
                                RegisterFile.updateRegister(operands[0], max);
                            }
                        }));

        // ============= MINIMUM INSTRUCTIONS =============
        instructionList.add(
                new BasicInstruction("min $t0, $t1,$t2",
                        "Minimum: sets $t0 to the smaller value between $t1 and $t2",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 101010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[1]);
                                int reg2 = RegisterFile.getValue(operands[2]);

                                int min = Math.min(reg1,reg2);
                                RegisterFile.updateRegister(operands[0], min);
                            }
                        }));
         instructionList.add(
                new BasicInstruction("mini $t1,$t2,150",
                        "minimum immediate with overflow : set $t1 to the smaller value between $t2 and a 16 bit immediate",
                        BasicInstructionFormat.I_FORMAT,
                        "101010 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[1]);
                                int immediate = operands[2] << 16 >> 16;
                                int min = Math.min(reg1,immediate);
                                RegisterFile.updateRegister(operands[0], min);
                            }
                        }));
        //Uniqe Instructions

        instructionList.add(
                new BasicInstruction("pow $t0, $t1, 2",
                        "Power: sets $t0 to ($t1 to the power of an immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "110000 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int base = RegisterFile.getValue(operands[1]);
                                int exponent = operands[2] << 16 >> 16;  // Sign-extend

                                if (exponent < 0)
                                {
                                    throw new ProcessingException(statement,
                                            "negative exponent not supported", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }

                                long result = 1;
                                for (int i = 0; i < exponent; i++)
                                {
                                    result *= base;

                                    if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)
                                    {
                                        throw new ProcessingException(statement,
                                                "arithmetic overflow", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                    }
                                }
                                RegisterFile.updateRegister(operands[0], (int) result);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("mod $t1,$t2,75",
                        "modulo immediate with overflow : set $t1 to ($t2 modulo signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "110010 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[1]);
                                int immediate = operands[2] << 16 >> 16;
                                if (immediate == 0)
                                {
                                    throw new ProcessingException(statement,
                                            "divide by zero",Exceptions.DIVIDE_BY_ZERO_EXCEPTION);
                                }
                                int mod = reg1 % immediate;
                                RegisterFile.updateRegister(operands[0], mod);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("factorial $t0, $t1",
                        "Factorial: sets $t0 to the factorial of $t1 ",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 110010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int reg1 = RegisterFile.getValue(operands[1]);

                                if (reg1 < 0)
                                {
                                    throw new ProcessingException(statement,
                                            "factorial of negative number undefined",
                                            Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }

                                long factorial = 1;
                                for(int i = 1; i <= reg1; i++)
                                {
                                    factorial *= i;

                                    if (factorial > Integer.MAX_VALUE)
                                    {
                                        throw new ProcessingException(statement,
                                                "arithmetic overflow",
                                                Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                    }
                                }
                                RegisterFile.updateRegister(operands[0], (int) factorial);
                            }
                        }));
        // ============= PRINT =============

// Print string literal
        instructionList.add(
                new BasicInstruction("print \"Hello!\"",
                        "Print: prints string literal to console",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 00000 00000 110100",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                String instructionText = statement.getSource();
                                int firstQuote = instructionText.indexOf('"');
                                int lastQuote = instructionText.lastIndexOf('"');
                                if (firstQuote != -1 && lastQuote != -1 && firstQuote != lastQuote)
                                {
                                    String rawMessage = instructionText.substring(firstQuote + 1, lastQuote);
                                    String processedMessage = rawMessage.replace("\\n", "\n");
                                    SystemIO.printString(processedMessage);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("printnum $t0",
                        "Print number: prints register value to console",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 001000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int value = RegisterFile.getValue(operands[0]);
                                SystemIO.printString(Integer.toString(value));
                            }
                        }));

        instructionList.add(
                new BasicInstruction("lstri $t2, \"Hello World!\"",
                        "Load string immediate: stores string literal in memory and loads its address into register",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 111010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                
                                // Extract string from instruction source
                                String instructionText = statement.getSource();
                                int firstQuote = instructionText.indexOf('"');
                                int lastQuote = instructionText.lastIndexOf('"');
                                
                                if (firstQuote != -1 && lastQuote != -1 && firstQuote != lastQuote)
                                {
                                    // Get the string content (without quotes)
                                    String rawString = instructionText.substring(firstQuote + 1, lastQuote);
                                    // Process escape sequences (like \n)
                                    String processedString = rawString.replace("\\n", "\n");
                                    
                                    // Store string in memory at data segment address
                                    // Use static counter to allocate space for each string
                                    int stringAddress = nextStringAddress;
                                    
                                    try
                                    {
                                        // Write each character of the string to memory
                                        for (int i = 0; i < processedString.length(); i++)
                                        {
                                            Globals.memory.setByte(stringAddress + i, processedString.charAt(i));
                                        }
                                        // Add null terminator
                                        Globals.memory.setByte(stringAddress + processedString.length(), 0);
                                        
                                        // Update next available address (align to word boundary for safety)
                                        nextStringAddress = stringAddress + processedString.length() + 1;
                                        nextStringAddress = (nextStringAddress + 3) & ~3; // Align to 4-byte boundary
                                        
                                        // Load the address into the register
                                        RegisterFile.updateRegister(operands[0], stringAddress);
                                    }
                                    catch (AddressErrorException e)
                                    {
                                        throw new ProcessingException(statement,
                                                "invalid memory address", Exceptions.ADDRESS_EXCEPTION_STORE);
                                    }
                                }
                            }
                        }));


// ============= STRING LENGTH =============

        instructionList.add(
                new BasicInstruction("strlen $t1,$t0",
                        "String length: stores length of string at address in $t0 into $t1",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss 00000 fffff 00000 110100",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int address = RegisterFile.getValue(operands[1]);

                                try
                                {
                                    // Read string from memory until null terminator
                                    int length = 0;
                                    while (true)
                                    {
                                        int byteValue = Globals.memory.getByte(address + length);
                                        if (byteValue == 0) break;  // Null terminator
                                        length++;
                                    }

                                    RegisterFile.updateRegister(operands[0], length);
                                }
                                catch (AddressErrorException e)
                                {
                                    throw new ProcessingException(statement,
                                            "invalid memory address", Exceptions.ADDRESS_EXCEPTION_LOAD);
                                }
                            }
                        }));

// ============= RANDOM =============

        instructionList.add(
                new BasicInstruction("rand $t1,$t0",
                        "Random: generates random value between 0 and $t0, stores in $t1",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss 00000 fffff 00000 110101",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int maxValue = RegisterFile.getValue(operands[1]);

                                if (maxValue <= 0)
                                {
                                    throw new ProcessingException(statement,
                                            "random range must be positive", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }

                                Random random = new Random();
                                int randomValue = random.nextInt(maxValue + 1);  // 0 to maxValue inclusive

                                RegisterFile.updateRegister(operands[0], randomValue);
                            }
                        }));

// ============= AVERAGE =============

        instructionList.add(
                new BasicInstruction("avg $t2,$t0,$t1",
                        "Average: calculates and stores average of $t0 and $t1 in $t2",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 110110",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int value1 = RegisterFile.getValue(operands[1]);
                                int value2 = RegisterFile.getValue(operands[2]);

                                // Use long to avoid overflow in addition
                                long sum = (long) value1 + (long) value2;
                                int average = (int) (sum / 2);

                                RegisterFile.updateRegister(operands[0], average);
                            }
                        }));

// ============= PALINDROME =============

        instructionList.add(
                new BasicInstruction("pali $t0,label",
                        "Palindrome: branches to label if string at address in $t0 is not a palindrome",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "110111 fffff 00000 ssssssssssssssss",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int address = RegisterFile.getValue(operands[0]);

                                try
                                {
                                    // Read string from memory
                                    StringBuilder str = new StringBuilder();
                                    int offset = 0;
                                    while (true)
                                    {
                                        int byteValue = Globals.memory.getByte(address + offset);
                                        if (byteValue == 0) break;  // Null terminator
                                        str.append((char) byteValue);
                                        offset++;
                                    }

                                    // Check if palindrome
                                    String original = str.toString();
                                    StringBuilder reversedBuilder = new StringBuilder(original);
                                    String reversed = reversedBuilder.reverse().toString();

                                    if (!original.equals(reversed))
                                    {
                                        Globals.instructionSet.processBranch(operands[1]);
                                    }
                                }
                                catch (AddressErrorException e)
                                {
                                    throw new ProcessingException(statement,
                                            "invalid memory address", Exceptions.ADDRESS_EXCEPTION_LOAD);
                                }
                            }
                        }));

// ============= GREATEST COMMON DIVISOR =============

        instructionList.add(
                new BasicInstruction("gcd $t2,$t0,$t1",
                        "GCD: calculates and stores greatest common divisor of $t0 and $t1 in $t2",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 111000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int a = Math.abs(RegisterFile.getValue(operands[1]));
                                int b = Math.abs(RegisterFile.getValue(operands[2]));

                                // Euclidean algorithm
                                while (b != 0)
                                {
                                    int temp = b;
                                    b = a % b;
                                    a = temp;
                                }

                                RegisterFile.updateRegister(operands[0], a);
                            }
                        }));

// ============= INPUT =============

        instructionList.add(
                new BasicInstruction("input $t0",
                        "Input: reads user input string and stores address in $t0",
                        BasicInstructionFormat.I_FORMAT,
                        "111001 fffff 00000 0000000000000000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();

                                // Use a static address in the data segment
                                // 0x10010000 is typically the start of the static data segment in MARS
                                int address = 0x10010000;
                                int maxLength = 256;

                                // Read string using MARS SystemIO (service number 8, max length)
                                String inputString = SystemIO.readString(8, maxLength);

                                // Write string to memory
                                try
                                {
                                    for (int i = 0; i < inputString.length(); i++)
                                    {
                                        Globals.memory.setByte(address + i, inputString.charAt(i));
                                    }
                                    // Add null terminator
                                    Globals.memory.setByte(address + inputString.length(), 0);
                                }
                                catch (AddressErrorException e)
                                {
                                    throw new ProcessingException(statement,
                                            "invalid memory address", Exceptions.ADDRESS_EXCEPTION_STORE);
                                }

                                // Store address in register
                                RegisterFile.updateRegister(operands[0], address);
                            }
                        }));

        // ============= JUMP INSTRUCTION =============
        instructionList.add(
                new BasicInstruction("j target",
                        "Jump unconditionally : Jump to statement at target address",
                        BasicInstructionFormat.J_FORMAT,
                        "000010 ffffffffffffffffffffffffff",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                Globals.instructionSet.processJump(
                                        ((RegisterFile.getProgramCounter() & 0xF0000000)
                                                | (operands[0] << 2)));
                            }
                        }));

    }
}
