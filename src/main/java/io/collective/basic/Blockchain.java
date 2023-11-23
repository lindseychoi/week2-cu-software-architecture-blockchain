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


        boolean isValid = false;
        // all this function needs to do is loop through a blockchain list and calculate if the hash
        // and previous hash match, that's it

            // todo - check an empty chain
        if (blockChainList.isEmpty()) {
            isValid = true;

            // todo - check a chain of one
        } else if (blockChainList.size() == 1) {
            //may need to do something here, but probably not, can't check
            //previous hash if there isn't a list, so just return that it's valid
            isValid = true;

            // todo - check a chain of many
        } else if (blockChainList.size() >= 2) {

            for (int i = 1; i < blockChainList.size(); i++) {
                //get the block right now and the last block to compare the hashes using
                //the gethash and getprevioushash methods from the block class
                Block thisBlock = blockChainList.get(i);
                Block lastBlock = blockChainList.get(i - 1);
                //compare this calcuatedhash to the current hash, if they don't match then return false
                //because they are not valid; do this for the block you're on and the previous block
                if (Objects.equals(thisBlock.getPreviousHash(), lastBlock.getHash())) {
                    isValid = true;
                } else if (!Objects.equals(thisBlock.getPreviousHash(), lastBlock.getHash())) {
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