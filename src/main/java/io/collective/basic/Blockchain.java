package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.collective.basic.Block.calculateHash;

public class Blockchain {
    private List<Block> blockChainList = new ArrayList<Block>();
    public boolean isEmpty() {
        return blockChainList.isEmpty();
    }

    public void add(Block block) {
        blockChainList.add(block);
    }

    public int size() {
        return blockChainList.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        boolean isValid = true;

        if (blockChainList.isEmpty()) {
            return true;
        } else if (blockChainList.size() == 1) {
            Block thisBlock = blockChainList.get(0);
            return Objects.equals(thisBlock.calculatedHash(), thisBlock.getHash());
        } else {
            for (int i = 1; i < blockChainList.size(); i++) {
                Block thisBlock = blockChainList.get(i);
                Block lastBlock = blockChainList.get(i - 1);

//   make sure that the previous hash in this block matches the last block's actual hash
                if(!Objects.equals(thisBlock.getPreviousHash(), lastBlock.getHash())) {
                    isValid = false;
                }
//  check that the calculated hash matches the actual hash in the block
                if (!Objects.equals(thisBlock.calculatedHash(), thisBlock.getHash())) {
                    System.out.println();
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
            String testHash = calculateHash(mined.getPreviousHash() + mined.getTimestamp() + mined.getNonce());
//            System.out.println("test hash" + testHash);
            mined.setHash(testHash);
        }

        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}