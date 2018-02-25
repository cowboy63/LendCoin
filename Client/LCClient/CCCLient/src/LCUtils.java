import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class LCUtils {
	private static final DateTimeFormatter STANDARD_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final void logEvent(String event)
	{
		System.out.println("[" + STANDARD_TIME_FORMAT.format(LocalDateTime.now()) + "] " + event);
	}
	
	public static final void parseParameters(String[] args)
	{
		if(args.length == 0)
		{
			logEvent("WARNING - No parameters specified; miner not initialized. Please specify mining parameters.");
			logEvent("INFO - Miner requires at least a miner address in order to operate.");
		}
		for(int i = 0; i < args.length; i++)
		{
			if(args[i].matches("-a"))
			{
				if(i < args.length - 1)
				{
					if(args[i + 1].matches("\\p{XDigit}{512}"))
					{
						try
						{
							hex2Bytes(args[i + 1], LocalMinerConfig.MINER_ADDRESS);
						} catch (Exception e) {
							logEvent("ERROR - cannot parse mining address; please check that your address is properly entered as a 512-digit hexadecimal string");
						}
					}
				}
				else
				{
					logEvent("WARNING - mining address flag specified without target address");
				}
			}
		}
	}
	
	public static final void hex2Bytes(String value, byte[] destination)
	{
		for(int i = 0; i < value.length(); i+=2)
		{
			destination[i >> 1] = (byte)Integer.parseInt(value.substring(i, i + 2), 16);
		}
	}
	
	public static final String bytes2Hex(byte[] source)
	{
		String result = "";
		for(int i = 0; i < source.length; i++)
		{
			result += String.format("%02X", source[i]);
		}
		return result;
	}
	
	public static final void long2Bytes(long value, byte[] dest)
	{
		for(int i = 0; i < 8; i++)
		{
			dest[7 - i] = (byte)(value & 0xFF);
			value >>>= 8;
		}
	}
	
	public static final long concatBigEndian(byte[] data, int start, int end)
	{
		long val = 0;
		for(int i = 0; i < end - start; i++)
		{
			val = (val << 8) | (data[start + i] & 0xFF);
		}
		return val;
	}
}
