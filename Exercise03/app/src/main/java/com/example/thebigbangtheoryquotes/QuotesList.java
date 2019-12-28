package com.example.thebigbangtheoryquotes;

import java.util.ArrayList;
import java.util.List;

public class QuotesList {

    private static List<Quote> quotes = fillWithQuotes();

    private static List<Quote> fillWithQuotes() {

        List<Quote> quotes = new ArrayList<>();

        quotes.add(new Quote( R.drawable.sheldon_cooper,
                "Interesting. You're afraid of insects and women." +
                        " Ladybugs must render you catatonic.", "Sheldon Cooper"));
        quotes.add(new Quote( R.drawable.sheldon_cooper,
                "Scissors cuts paper, paper covers rock, rock crushes lizard, " +
                        "lizard poisons Spock, Spock smashes scissors, scissors decapitates lizard, " +
                        "lizard eats paper, paper disproves Spock, Spock vaporizes rock, and as it " +
                        "always has, rock crushes scissors.", "Sheldon Cooper"));
        quotes.add(new Quote( R.drawable.sheldon_cooper,
                "Then it's settled. Amy's birthday present will be my genitals.",
                "Sheldon Cooper"));
        quotes.add(new Quote( R.drawable.amy_farrah_fowler,
                "As my mom used to say, \"When you're doing a puzzle, it's like you've got a" +
                        " thousand friends.\" She was full of fun lies like that.",
                "Amy Farrah Fowler"));
        quotes.add(new Quote( R.drawable.amy_farrah_fowler,
                "I gave you one job! Keep an eye on him. How hard is that?",
                "Amy Farrah Fowler"));
        quotes.add(new Quote( R.drawable.amy_farrah_fowler,
                "You better watch that attitude, buddy. You're dating the popular girl now.",
                "Amy Farrah Fowler"));
        quotes.add(new Quote( R.drawable.howard_wolowitz,
                "Smart. Whisper so the deaf chick doesn't hear you!",
                "Howard Wolowitz"));
        quotes.add(new Quote( R.drawable.howard_wolowitz,
                "That's more like we're a tall thin woman who wants to make a coat out" +
                        " of your Dalmatians.", "Howard Wolowitz"));
        quotes.add(new Quote( R.drawable.howard_wolowitz,
                "If it's \"creepy\" to use the Internet, military satellites, and robot " +
                        "aircraft to find a house full of gorgeous young models so I can drop in on" +
                        " them unexpected, then fine, I'm \"creepy\".", "Howard Wolowitz"));
        quotes.add(new Quote( R.drawable.penny,
                "I love him, but if he's broken, let's not get a new one.", "Penny"));
        quotes.add(new Quote( R.drawable.penny,
                "So that means, you're a doctor, you're a doctor, you're a doctor, you're " +
                        "a doctor and, Howard, you know a lot of doctors.","Penny"));
        quotes.add(new Quote( R.drawable.penny,
                "Sheldon, that's not what girlfriends are for. Although you don't use them " +
                        "for what they're for, so what do I know?","Penny"));
        quotes.add(new Quote( R.drawable.leonard_hofstadter,
                "We're here to see Koothrappali, not kill Batman.", "Leonard Hofstadter"));
        quotes.add(new Quote( R.drawable.leonard_hofstadter,
                "People get things they don't deserve all the time. Like me with you.",
                "Leonard Hofstadter"));
        quotes.add(new Quote( R.drawable.leonard_hofstadter,
                "Sometimes your movements are so lifelike I forget you are not a real boy.",
                "Leonard Hofstadter"));
        quotes.add(new Quote( R.drawable.raj_koothrappali,
                "I've said this before and I'll say it again: Aquaman sucks!",
                "Raj Koothrappali"));
        quotes.add(new Quote( R.drawable.raj_koothrappali,
                "Amy, good luck getting these guys excited about a dinner with a theme." +
                        " I gave up when no one cared about my Tom Hanks-Giving.",
                "Raj Koothrappali"));
        quotes.add(new Quote( R.drawable.raj_koothrappali,
                "It's too late. He's been murdered by someone in this room. " +
                        "Welcome to another classic Koothrappali murder mystery dinner.",
                "Raj Koothrappali"));
        quotes.add(new Quote( R.drawable.bernadette_rostenkowski,
                "You better find my husband's mother, 'cause one way or another we're walking" +
                        " out of this airport with a dead woman.", "Bernadette Rostenkowski"));
        quotes.add(new Quote( R.drawable.bernadette_rostenkowski,
                "I told you you shouldn't have espresso after dinner. I know the little cups " +
                        "make you feel big but it's not worth it.", "Bernadette Rostenkowski"));
        quotes.add(new Quote( R.drawable.bernadette_rostenkowski,
                "Gosh, Amy. I'm sensing a little hostility. Is it maybe because like Sheldon's" +
                        " work, your sex life is also theoretical?", "Bernadette Rostenkowski"));

        return quotes;
    }

    public static Quote getRandomQuote(){

        int size = quotes.size();
        Quote q = quotes.get((int)Math.round((Math.random() * size) - 0.00001));

        return q;
    }
}
