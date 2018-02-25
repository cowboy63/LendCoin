
public class LCMiner {
	public static final void main(final String[] args)
	{
		System.out.println("+===============================================+");
		System.out.println("|Lendcoin Miner v0.1.1, made with love by DaBois|");
		System.out.println("+===============================================+");
		
		LCUtils.logEvent("INFO - Parsing mining parameters...");
		LCUtils.parseParameters(args);
		
		Transaction tc = new Transaction("428acdddde84", "494902409865", 30, 10, 5, 100, "093034", "4309548D55");
		Block bk = new Block(0, 1, 1, 1, "49059873456879654783989745", "891234", new Transaction[] {tc}, "13", "298455");
		System.out.println(bk);
	}
}
