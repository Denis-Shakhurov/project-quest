@import dto.GamePage
@param GamePage page

@template.layout.page(
content = @`
    <section>
        <div class="container-lg mt-5">
            @if (page.getFlash() != null && page.getFlash().equals("End game"))
                <div class="alert alert-success w-25 p-3 position-absolute top-50 start-50 translate-middle" role="alert">
                    <h4 class="alert-heading text-center">Ура, победа!</h4>
                    <hr>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-outline-primary"><a href="/">На главную</a></button>
                        <button type="button" class="btn btn-outline-primary"><a href="/users/${page.getGame().getUserId()}">К списку игр</a></button>
                    </div>
                </div>
            @elseif (page.getFlash() != null && page.getFlash().equals("Fail!"))
                <div class="alert alert-danger w-25 p-3 position-absolute top-50 start-50 translate-middle" role="alert">
                    <h4 class="alert-heading text-center">Увы, поражение!</h4>
                    <hr>
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-outline-primary"><a href="/">На главную</a></button>
                        <button type="button" class="btn btn-outline-primary"><a href="/users/${page.getGame().getUserId()}">К списку игр</a></button>
                    </div>
                </div>
            @else
            <h1>${page.getGame().getName()}</h1>
            <p>${page.getGame().getDescription()}: ${page.getQuestion()}</p>
            <form action="/games/${page.getGame().getId()}" method="post" class="rss-form text-body">
                <div class="row">
                    <div class="col">
                        <div class="form-floating">
                            <input id="name-input" autofocus="" type="text" required="" name="answer" aria-label="name"
                                   class="form-control w-50" placeholder="введи ответ" autocomplete="off">
                            <label for="name-input">Введи ответ</label>
                        </div>
                    </div>
                    <div class="col">
                        <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-3">Проверить</button>
                    </div>
                </div>
            </form>
            <p>${page.getFlash()}</p>
            @endif
        </div>
    </section>
`
)