/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.factor;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.CreatureType;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.LocationType;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.ProductProperty;
import pl.projewski.game.polan.data.Role;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.data.ProductDefinition;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.util.RandomElement;
import pl.projewski.game.polan.server.work.IWork;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorldManager {

    private static Set<World> worldSet = new HashSet();

    public static World loadFromFile(final File file) throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            gson.fromJson(reader, World.class);
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return null;
    }

    public static void storeToFile(final File file, final World world) throws IOException {
        Gson gson = new Gson();
        Writer writer = null;
        try {
            writer = new FileWriter(file);
            gson.toJson(world, writer);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public static Set<World> listWorlds() {
        return worldSet;
    }

    /**
     * Find connection between to locations. In return is a result with all locations between location A and location B. Algorithm: should start from searching
     * always from startlocation to location A and to locaton B as a seperate lists. Algorithm stops until find these two locations or cannot find any
     * connection. After find it should check that lists are from the same root and remove as posible as can the same elements.
     *
     * @param locationIdA
     * @param locationIdB
     * @return
     */
    public List<Location> findConnection(int locationIdA, int locationIdB) {
        // TODO:
        return null;
    }

    public static Product generateProcudt(ProductDefinition productName) {
        final Product product = new Product();
        product.setName(productName.getName());
        return product;
    }

    // public Product
    public static void randomElements(final Location location, final Random random) {
        // TODO: Firstly random Fields and as second - what can be on this field (Tree)
        int size = location.getSize();
        RandomElement<ProductDefinition> randomProducts = new RandomElement();
        LocationType type = location.getType();
        switch (type) {
            case PLANES:
                randomProducts.addRandomElement(ProductDefinition.GRASS_FIELD, 30);
                randomProducts.addRandomElement(ProductDefinition.CLOVER_FIELD, 10);
                randomProducts.addRandomElement(ProductDefinition.WATER_FIELD, 5);
                randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 10);
                break;
            case FOREST:
                randomProducts.addRandomElement(ProductDefinition.OAK_TREE, 30);
                randomProducts.addRandomElement(ProductDefinition.PINE_TREE, 30);
                randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 30);
                randomProducts.addRandomElement(ProductDefinition.WATER_FIELD, 5);
                randomProducts.addRandomElement(ProductDefinition.GRASS_FIELD, 5);
                randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 30);
                break;
            case TAIGA:
                randomProducts.addRandomElement(ProductDefinition.PINE_TREE, 30);
                randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 10);
                randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 40);
                break;
            case TUNDRA:
                randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 10);
                randomProducts.addRandomElement(ProductDefinition.PINE_TREE, 10);
                randomProducts.addRandomElement(ProductDefinition.ICE_FIELD, 20);
                randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 30);
                break;
            case DESERT:
                randomProducts.addRandomElement(ProductDefinition.SAND_FIELD, 30);
                randomProducts.addRandomElement(ProductDefinition.SAND_STONE_FIELD, 10);
                break;
            case SWAMPS:
                randomProducts.addRandomElement(ProductDefinition.WATER_FIELD, 10);
                randomProducts.addRandomElement(ProductDefinition.CALAMUS_PLANT, 20);
                randomProducts.addRandomElement(ProductDefinition.MUD_FIELD, 30);
                randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 5);
                randomProducts.addRandomElement(ProductDefinition.GRASS_FIELD, 5);
                break;
            case MOUNTAINS:
                randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 10);
                randomProducts.addRandomElement(ProductDefinition.STONE_FIELD, 30);
                randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 5);
                break;
            case HILLS:
                randomProducts.addRandomElement(ProductDefinition.STONE_FIELD, 30);
                randomProducts.addRandomElement(ProductDefinition.GRANITE_STONE_FIELD, 30);
                randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 10);
                break;
            case OCEAN:
                randomProducts.addRandomElement(ProductDefinition.WATER_FIELD, 30);
                break;
            case BEACH:
                randomProducts.addRandomElement(ProductDefinition.SAND_FIELD, 30);
                randomProducts.addRandomElement(ProductDefinition.GRASS_FIELD, 1);
                break;
            default:
                throw new AssertionError(type.name());
        }
        while (size > 0) {
            location.addElement(generateProcudt(randomProducts.getRandomElement(random)));
            size--;
        }
    }

    public static void exploreNewLocation(World world, Location location, boolean isStart, User user) {
        // TODO: Random engine should be stable by random seed for whole world
        Random random = new Random();
        LocationType[] locationTypes = LocationType.values();
        // count global number (it's let say a weight of possibility of exists each type as startup)
        // TODO: it should be inside some configuration
        // TODO: if it comes from another location it should be depend what type can be / cannot be generated next
        RandomElement<LocationType> randomStartLocationType = new RandomElement();
        randomStartLocationType.addRandomElement(LocationType.PLANES, 30);
        randomStartLocationType.addRandomElement(LocationType.FOREST, 20);
        randomStartLocationType.addRandomElement(LocationType.TAIGA, 5);
        randomStartLocationType.addRandomElement(LocationType.TUNDRA, 2);
        randomStartLocationType.addRandomElement(LocationType.DESERT, 5);
        randomStartLocationType.addRandomElement(LocationType.SWAMPS, 5);
        randomStartLocationType.addRandomElement(LocationType.MOUNTAINS, 2);
        randomStartLocationType.addRandomElement(LocationType.HILLS, 1);
        randomStartLocationType.addRandomElement(LocationType.BEACH, 1);
        location.setType(randomStartLocationType.getRandomElement(random));
        location.setKnownByUser(true);
        location.setUsername(user.getName());
        location.setId(world.generateNewLocationId());
        final int size = 10 + random.nextInt(20);
        // TODO: now let say it's between 10 and 29. It should be depend on type, start and maybe other things (and maybe little more)
        location.setSize(size);
        randomElements(location, random);
        // generate new locations for new known loaction (and start is always known)
        // TODO: it should be seperate method
        // if there are connections - not generate to much more
        int possibleConnections = (isStart ? 2 + random.nextInt(3) : random.nextInt(5))
                - (location.getConnection() != null ? location.getConnection().size() : 0);
        while (possibleConnections > 0) {
            // new location is unknown and not generated
            // it would be generated, when it is discovered/explored by user
            // TODO: In some cases of existing more than one location it is possible to connect 2 start points roots
            //       Connection will be possible between two unknown locations (and they're new for both start points)
            //       In some case this will be a little risky, because of storing by Json and recursive writing - TO FIX idea
            Location newLocation = new Location();
            newLocation.addConnection(location);
            newLocation.setId(world.generateNewLocationId());
            location.addConnection(newLocation);
            world.addLocation(newLocation);
            possibleConnections--;
        }
    }

    public static World createNewWorld(String worldName) {
        if (getWorld(worldName) != null) {
            return null;
        }
        World world = new World(worldName);
        worldSet.add(world);
        return world;
    }

    public static World getWorld(String worldName) {
        if (worldName == null) {
            return null;
        }
        for (World world : worldSet) {
            if (world.getName().equals(worldName)) {
                return world;
            }
        }
        return null;
    }

    public static Location createTown(World world, User user) {
        Location startLocation = new Location();
        world.addLocation(startLocation);
        WorldManager.exploreNewLocation(world, startLocation, true, user);
        Creature human = new Creature(user.getName(), user.getName(), CreatureType.HUMAN);
        human.assignRole(Role.KING);
        human.setLocationId(startLocation.getId());
        human.setActualRole(Role.KING);
        human.setId(world.generateNewHumanId());
        world.addHuman(human);
        return startLocation;
    }

    public static Location getLocation(World world, Integer locationId) {
        List<Location> locations = world.getLocations();
        for (Location location : locations) {
            if (location.getId() == locationId.intValue()) {
                return location;
            }
        }
        return null;
    }

    public static void addWorlds(Collection<World> worlds) {
        if (worlds == null) {
            return;
        }
        worldSet.addAll(worlds);
    }

    public static List<Creature> findHumans(World world, int locationId) {
        if (world == null) {
            return null;
        }
        final List<Creature> result = new ArrayList();
        final List<Creature> humans = world.getHumans();
        for (final Creature human : humans) {
            if (human.getLocationId() == locationId) {
                result.add(human);
            }
        }
        return result;
    }

    public static Creature getCreature(World world, int humanId) {
        if (world == null) {
            return null;
        }
        List<Creature> humans = world.getHumans();
        for (Creature human : humans) {
            if (human.getId() == humanId) {
                return human;
            }
        }
        return null;
    }

    private static Map<String, WorldWorkQueue> worldQueues = new HashMap();

    public static void addWork(World world, IWork work) {
        WorldWorkQueue queue = worldQueues.get(world.getName());
        if (queue == null) {
            queue = new WorldWorkQueue();
            worldQueues.put(world.getName(), queue);
        }
        queue.addWork(work);
    }

    public static void nextTicks(World world, int numOfTicks) {
        world.addWorldTime(numOfTicks);
        WorldWorkQueue queue = worldQueues.get(world.getName());
        if (queue != null) {
            queue.doPlanedWorks(numOfTicks);
        }
    }

}
