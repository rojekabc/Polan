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
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.Role;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.data.PolanServerConfiguration;
import pl.projewski.game.polan.server.data.ProductType;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.util.RandomElement;
import pl.projewski.game.polan.server.work.AWorkerWork;
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
     * Find connection between to locations. In return is a result with all locations between location A and location B.
     * Algorithm: should start from searching always from startlocation to location A and to locaton B as a seperate
     * lists. Algorithm stops until find these two locations or cannot find any connection. After find it should check
     * that lists are from the same root and remove as posible as can the same elements.
     *
     * @param locationIdA
     * @param locationIdB
     * @return
     */
    public List<Location> findConnection(int locationIdA, int locationIdB) {
        // TODO:
        return null;
    }

    public static Product generateProcudt(final World world, ProductDefinition productName) {
        final Product product = new Product(world.generateProductId());
        product.setName(productName.getName());
        world.addProduct(product);
        return product;
    }

    // public Product
    public static void randomElements(final World world, final Location location, final Random random) {
        // TODO: Firstly random Fields and as second - what can be on this field (Tree)
        int size = location.getSize();
        String type = location.getType();
        RandomElement<ProductDefinition> randomProducts = ServerData.getInstance().getBiomeElements(type);
        while (size > 0) {
            final ProductDefinition productDefinition = randomProducts.getRandomElement(random);
            final Product newElement = generateProcudt(world, productDefinition);
            if (productDefinition.getType() != ProductType.FIELD) {
                List<String> possibleExistsOn = productDefinition.getPossibleExistsOn();

                ProductDefinition randomField = ServerData.getInstance().getBiomeFieldForProduct(type, productDefinition).getRandomElement(random);
                // no field, where I may put element in this type of biome
                if (randomField == null) {
                    continue;
                }
                final Product field = generateProcudt(world, randomField);
                field.addElement(newElement);
                location.addElement(field);
            } else {
                location.addElement(newElement);
            }
            size--;
        }
    }

    public static void exploreNewLocation(World world, Location location, boolean isStart, User user) {
        // TODO: Random engine should be stable by random seed for whole world
        Random random = new Random();
        // count global number (it's let say a weight of possibility of exists each type as startup)
        // TODO: it should be inside some configuration
        // TODO: if it comes from another location it should be depend what type can be / cannot be generated next
        location.setType(ServerData.getInstance().getBiomes().getRandomElement(random).getName());
        location.setKnownByUser(true);
        location.setUsername(user.getName());
        if (isStart) {
            location.setId(world.generateNewLocationId());
        }
        final int size = PolanServerConfiguration.BIOME_SIZE_MINIMUM
                + random.nextInt(PolanServerConfiguration.BIOME_SIZE_MAXIMUM - PolanServerConfiguration.BIOME_SIZE_MINIMUM);
        location.setSize(size);
        randomElements(world, location, random);
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
        world.addCreature(human);
        return startLocation;
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
        final Collection<Creature> humans = world.getCreatures();
        for (final Creature human : humans) {
            if (human.getLocationId() == locationId) {
                result.add(human);
            }
        }
        return result;
    }

    // TODO: This queue should be stored in World and with works possible to write into gson store save file
    // TODO: Currently it's not done and works are not saved
    // TODO: It need to a refactor to construct way of write work with id to elements, not with objects
    private static Map<String, WorldWorkQueue> worldQueues = new HashMap();

    /**
     * Add new work to queue for world. If work want use some creature it's checked, that creature is free. If it's
     * working on another work - he'll be free and assign to new work.
     *
     * @param world
     * @param work
     */
    public static void addWork(final World world, final IWork work) {
        WorldWorkQueue queue = worldQueues.get(world.getName());
        if (queue == null) {
            queue = new WorldWorkQueue();
            worldQueues.put(world.getName(), queue);
        }
        // check if worker is taking care about another job
        if (work instanceof AWorkerWork) {
            IWork currentWork = queue.findWorkOfCreature(((AWorkerWork) work).getWorker());
            if (currentWork != null) {
                breakWork(world, queue, currentWork);
            }
        }
        // append new work to queue
        queue.addWork(work);
        work.initWork(world);
    }

    private static void breakWork(final World world, final WorldWorkQueue queue, final IWork work) {
        work.breakWork(world);
        queue.removeWork(work);
    }

    public static void nextTick(World world) {
        world.nextWorldTime();
        WorldWorkQueue queue = worldQueues.get(world.getName());
        if (queue != null) {
            queue.doPlanedWorks(world);
        }
    }

}
